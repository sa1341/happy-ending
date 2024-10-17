package com.kakaopaysec.happyending.batch.common

import com.querydsl.core.DefaultQueryMetadata
import com.querydsl.core.JoinFlag
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.NumberPath
import com.querydsl.core.util.CollectionUtils
import com.querydsl.jpa.JPAQueryMixin
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManagerFactory
import mu.KotlinLogging
import org.springframework.data.domain.Sort
import java.util.concurrent.atomic.AtomicLong

private val logger = KotlinLogging.logger { }

open class QueryDslZeroOffsetItemReader<T : Any>(
    name: String?,
    pageSize: Int,
    entityManagerFactory: EntityManagerFactory,
    private val idAndSort: IdAndSort,
    private val idField: NumberPath<Long>,
) : QuerydslItemReader<T>(
    name,
    pageSize,
    entityManagerFactory
) {

    constructor(
        name: String?,
        pageSize: Int,
        entityManagerFactory: EntityManagerFactory,
        idAndSort: IdAndSort,
        idField: NumberPath<Long>,
        query: QuerydslItemReaderQuery<T>,
    ) : this(
        name,
        pageSize,
        entityManagerFactory,
        idAndSort,
        idField
    ) {
        super.query = query
    }

    private val offsetId = AtomicLong(0)

    private val lastId = AtomicLong(-1)

    private val variationOfOffset = if (idAndSort.isAsc) pageSize else -pageSize

    override fun doReadPage() {
        fun hasNextChunk(): Boolean =
            (offsetId.get() to lastId.get()).let { (offset, lastId) ->
                results.isEmpty() && if (idAndSort.isAsc) offset <= lastId else offset > -1
            }

        while (hasNextChunk()) {
            val entityTransaction = getTransaction()

            logger.info { "오프셋 ${offsetId.get()}부터 $pageSize 개의 아이템을 읽습니다." }
            createQuery().fetchQuery(entityTransaction)
            logger.info { "실제로 읽은 레코드 개수는 ${results.size}입니다." }

            offsetId.updateAndGet { it + variationOfOffset }
        }
    }

    override fun createQuery(): JPAQuery<T> =
        query.invoke(JPAQueryFactory(entityManager))
            .where(idAndSort.where(idField, offsetId.get(), pageSize))
            .orderBy(idAndSort.order(idField))

    override fun doOpen() {
        super.doOpen()

        // JPAQuery의 metadata를 reflection을 사용하여 fetch join 플래그 값을 제거함
        @Suppress("UNCHECKED_CAST")
        fun cancelFetchJoinFlag(): JPAQuery<T> =
            query.invoke(JPAQueryFactory(entityManager)).also { jpaQuery ->
                val metadata = jpaQuery.metadata

                if (metadata !is DefaultQueryMetadata) return@also

                val joinFlagsField = metadata.javaClass.declaredFields
                    .first { it.name == "joinFlags" }
                    .also { it.isAccessible = true }

                val joinFlags = joinFlagsField.get(metadata) as Set<JoinFlag>

                if (joinFlags.contains(JPAQueryMixin.FETCH)) {
                    joinFlagsField.set(metadata, CollectionUtils.removeSorted(joinFlags, JPAQueryMixin.FETCH))
                }
            }

        val fetchJoinCanceledQuery = cancelFetchJoinFlag()

        offsetId.set(
            fetchJoinCanceledQuery
                .select(if (idAndSort.isAsc) idField.min() else idField.max())
                .fetchOne() ?: 0
        )

        lastId.set(
            fetchJoinCanceledQuery
                .select(if (idAndSort.isAsc) idField.max() else idField.min())
                .fetchOne() ?: -1
        )

        logger.info { "오프셋 $offsetId 부터 $lastId 번까지 아이템 읽기를 시작합니다." }
    }

    override fun doClose() {
        try {
            super.doClose()

            if (offsetId.get() >= lastId.get()) {
                logger.info { "$lastId 까지 아이템을 모두 읽었습니다." }
            } else {
                logger.warn { "$lastId 까지의 아이템을 모두 읽지 못했습니다 (현재 오프셋: $offsetId)" }
            }
        } finally {
            offsetId.set(0)
            lastId.set(-1)
        }
    }
}

open class IdAndSort(
    private val whereExpression: (id: NumberPath<Long>, offsetId: Long, pageSize: Int) -> BooleanExpression,
    private val direction: Sort.Direction,
) {
    val isAsc: Boolean
        get() = direction == Sort.Direction.ASC

    fun where(id: NumberPath<Long>, offsetId: Long, pageSize: Int): BooleanExpression =
        whereExpression.invoke(id, offsetId, pageSize)

    fun order(id: NumberPath<Long>): OrderSpecifier<Long> =
        if (isAsc) id.asc() else id.desc()
}

object Asc : IdAndSort({ id, offsetId, pageSize ->
    id.goe(offsetId).and(id.lt(offsetId + pageSize))
}, Sort.Direction.ASC)

object Desc : IdAndSort({ id, offsetId, pageSize ->
    id.gt(offsetId - pageSize).and(id.loe(offsetId))
}, Sort.Direction.DESC)
