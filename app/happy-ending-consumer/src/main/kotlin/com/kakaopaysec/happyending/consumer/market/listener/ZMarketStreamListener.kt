package com.kakaopaysec.happyending.consumer.market.listener

import com.kakaopaysec.happyending.consumer.market.model.Purchase
import com.kakaopaysec.happyending.consumer.market.model.PurchasePattern
import com.kakaopaysec.happyending.consumer.market.model.RewardAccumulator
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Produced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer

@EnableKafkaStreams
@Configuration
class ZMarketStreamListener {

    @Bean
    fun purchaseKStream(streamsBuilder: StreamsBuilder): KStream<String, Purchase> {
        val purchaseJsonSerdes = createPurchaseJsonSerdes()
        val purchaseKStream = streamsBuilder.stream(
            "transactions",
            Consumed.with(Serdes.String(), Serdes.serdeFrom(purchaseJsonSerdes.first, purchaseJsonSerdes.second))
        ).mapValues { p: Purchase ->
            Purchase.from(p)
        }

        val purchasePatternSerdes = createPurchasePatternJsonSerdes()
        val patternKStream = purchaseKStream
            .mapValues { p: Purchase -> PurchasePattern.from(p) }
            .to(
                "patterns",
                Produced.with(Serdes.String(), Serdes.serdeFrom(purchasePatternSerdes.first, purchasePatternSerdes.second))
            )

        val rewardAccumulatorSerdes = createRewardAccumulatorJsonSerdes()
        val rewardsKStream = purchaseKStream
            .mapValues { p: Purchase -> RewardAccumulator.from(p) }
            .to(
                "rewards",
                Produced.with(Serdes.String(), Serdes.serdeFrom(rewardAccumulatorSerdes.first, rewardAccumulatorSerdes.second))
            )

        purchaseKStream.to(
            "purchases",
            Produced.with(Serdes.String(), Serdes.serdeFrom(purchaseJsonSerdes.first, purchaseJsonSerdes.second))
        )

        return purchaseKStream
    }

    private fun createPurchaseJsonSerdes(): Pair<JsonSerializer<Purchase>, JsonDeserializer<Purchase>> {
        val jsonSerializer = JsonSerializer<Purchase>()
        val jsonDeSerializer = JsonDeserializer(Purchase::class.java)
        jsonDeSerializer.setRemoveTypeHeaders(false)
        jsonDeSerializer.addTrustedPackages("*")
        jsonDeSerializer.setUseTypeMapperForKey(true)
        return Pair(jsonSerializer, jsonDeSerializer)
    }

    private fun createPurchasePatternJsonSerdes(): Pair<JsonSerializer<PurchasePattern>, JsonDeserializer<PurchasePattern>> {
        val jsonSerializer = JsonSerializer<PurchasePattern>()
        val jsonDeSerializer = JsonDeserializer(PurchasePattern::class.java)
        jsonDeSerializer.setRemoveTypeHeaders(false)
        jsonDeSerializer.addTrustedPackages("*")
        jsonDeSerializer.setUseTypeMapperForKey(true)
        return Pair(jsonSerializer, jsonDeSerializer)
    }

    private fun createRewardAccumulatorJsonSerdes(): Pair<JsonSerializer<RewardAccumulator>, JsonDeserializer<RewardAccumulator>> {
        val jsonSerializer = JsonSerializer<RewardAccumulator>()
        val jsonDeSerializer = JsonDeserializer(RewardAccumulator::class.java)
        jsonDeSerializer.setRemoveTypeHeaders(false)
        jsonDeSerializer.addTrustedPackages("*")
        jsonDeSerializer.setUseTypeMapperForKey(true)
        return Pair(jsonSerializer, jsonDeSerializer)
    }
}
