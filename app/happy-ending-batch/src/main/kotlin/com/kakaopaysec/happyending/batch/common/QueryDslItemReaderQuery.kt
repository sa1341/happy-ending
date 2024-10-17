package com.kakaopaysec.happyending.batch.common

import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.EntityTransaction
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader
import org.springframework.dao.DataAccessResourceFailureException
import org.springframework.util.ClassUtils
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger

typealias QuerydslItemReaderQuery<T> = (JPAQueryFactory) -> JPAQuery<T>

open class QuerydslItemReader<T>(
    name: String?,
    protected val pageSize: Int,
    protected val entityManagerFactory: EntityManagerFactory,
    private val jpaPropertyMap: Map<String, Any> = hashMapOf(),
) : AbstractItemCountingItemStreamItemReader<T>() {

    constructor(
        name: String?,
        pageSize: Int,
        entityManagerFactory: EntityManagerFactory,
        query: QuerydslItemReaderQuery<T>,
    ) : this(
        name,
        pageSize,
        entityManagerFactory
    ) {
        this.query = query
    }

    protected val results: MutableList<T> = CopyOnWriteArrayList()

    private val page = AtomicInteger(0)

    private var transacted: Boolean = true

    protected lateinit var query: QuerydslItemReaderQuery<T>

    protected lateinit var entityManager: EntityManager

    init {
        super.setName(name ?: ClassUtils.getShortName(this::class.java))
    }

    open fun doReadPage() {
        val entityTransaction = getTransaction()

        results.clear()

        createQuery()
            .offset(page.get() * pageSize)
            .limit(pageSize)
            .fetchQuery(entityTransaction)

        page.incrementAndGet()
    }

    override fun doRead(): T? {
        if (results.isEmpty()) {
            doReadPage()
        }

        return if (results.isEmpty()) null else results.removeAt(0)
    }

    protected open fun createQuery(): JPAQuery<T> = query.invoke(JPAQueryFactory(entityManager))

    protected fun getTransaction(): EntityTransaction? =
        if (transacted) {
            entityManager.transaction.also { entityTransaction ->
                entityTransaction.begin()
                entityManager.flush()
                entityManager.clear()
            }
        } else {
            null
        }

    override fun doOpen() {
        page.set(0)

        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap).let {
            if (it == null) {
                throw DataAccessResourceFailureException("Could not create entity manager")
            }
            it
        }
    }

    override fun doClose() {
        results.clear()
        page.set(0)

        if (::entityManager.isInitialized) {
            entityManager.close()
        }
    }

    protected fun JPAQuery<T>.offset(offset: Int): JPAQuery<T> = this.offset(offset.toLong())
    protected fun JPAQuery<T>.limit(limit: Int): JPAQuery<T> = this.limit(limit.toLong())

    protected fun JPAQuery<T>.fetchQuery(entityTransaction: EntityTransaction?) {
        this.fetch().let { queryResult ->
            if (transacted) {
                results.addAll(queryResult)
                entityTransaction?.commit()
            } else {
                queryResult.forEach { item ->
                    entityManager.detach(item)
                    results.add(item)
                }
            }
        }
    }
}
