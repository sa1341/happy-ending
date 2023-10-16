package com.kakaopaysec.happyending.consumer.stock

import com.kakaopaysec.happyending.consumer.stock.model.StockTickerData
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Printed
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration class StockKTableListener {

    @Bean
    fun stockKTable(streamsBuilder: StreamsBuilder) {
        val stockTickerTable = streamsBuilder.table<String, StockTickerData>(STOCK_TICKER_TABLE_TOPIC)
        val stockTickerStream = streamsBuilder.stream<String, StockTickerData>(STOCK_TICKER_STREAM_TOPIC)

        stockTickerTable.toStream().print(Printed.toSysOut<String?, StockTickerData?>().withLabel("Stocks-KTable"))
        stockTickerStream.print(Printed.toSysOut<String?, StockTickerData?>().withLabel("Stocks-KStream"))
    }

    companion object {
        private const val STOCK_TICKER_TABLE_TOPIC = "stock-ticker-table"
        private const val STOCK_TICKER_STREAM_TOPIC = "stock-ticker-stream"
    }
}
