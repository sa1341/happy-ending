package com.kakaopaysec.happyending.logging.logback

import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent

class MaskingPatternLayout : PatternLayout() {
    override fun doLayout(event: ILoggingEvent?): String {
        return MaskingPatterns.maskMessage(super.doLayout(event))
    }
}
