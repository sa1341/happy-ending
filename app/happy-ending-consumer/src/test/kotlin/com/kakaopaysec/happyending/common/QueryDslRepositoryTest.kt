package com.kakaopaysec.happyending.common

import com.querydsl.core.DefaultQueryMetadata
import com.querydsl.core.QueryMetadata
import com.querydsl.core.types.Order
import com.querydsl.core.types.dsl.Expressions
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class QueryDslRepositoryTest {

    @Test
    fun `QueryDsl Metadata 정보를 조회한다`() {
        // given, when
        val queryMetaData: QueryMetadata = DefaultQueryMetadata()
        val entity = Expressions.numberPath(Integer::class.java, "entity")
        queryMetaData.addWhere(entity.gt(10))
        queryMetaData.addOrderBy(entity.desc())

        // then
        queryMetaData.orderBy.first().order shouldBe Order.DESC
    }
}
