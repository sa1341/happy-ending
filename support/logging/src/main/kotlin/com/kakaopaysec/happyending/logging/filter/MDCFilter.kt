package com.kakaopaysec.happyending.logging.filter

import com.kakaopaysec.happyending.utils.GuidUtils
import com.kakaopaysec.happyending.utils.model.CommonHeader
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.context.annotation.Profile
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Profile("!test")
@Order(Ordered.LOWEST_PRECEDENCE - 2)
@Component
class MDCFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        GuidUtils.initGuidInfo(request.getHeader(CommonHeader.TRANSACTION_ID))
        filterChain.doFilter(request, response)
        MDC.clear()
    }
}
