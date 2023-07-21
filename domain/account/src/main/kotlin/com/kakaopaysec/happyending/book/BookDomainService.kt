package com.kakaopaysec.happyending.book

import com.kakaopaysec.happyending.book.model.Book
import com.kakaopaysec.happyending.service.SecuritiesClientService
import com.kakaopaysec.happyending.service.ServiceType
import com.kakaopaysec.utils.typeReference
import org.springframework.stereotype.Component

@Component
class BookDomainService(
    private val securitiesClientService: SecuritiesClientService
) {

    fun getBook(bookId: Long): Book {
        return securitiesClientService
            .callHttpByGet(
                serviceType = ServiceType.GET_BOOK,
                bookId = bookId,
                responseType = typeReference<Book>()
            )
    }
}
