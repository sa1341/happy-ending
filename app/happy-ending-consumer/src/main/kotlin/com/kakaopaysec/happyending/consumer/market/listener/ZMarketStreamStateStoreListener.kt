package com.kakaopaysec.happyending.consumer.market.listener

import com.kakaopaysec.happyending.consumer.config.KafkaJacksonConfig.Companion.createPurchaseJsonSerdes
import com.kakaopaysec.happyending.consumer.config.KafkaJacksonConfig.Companion.createPurchasePatternJsonSerdes
import com.kakaopaysec.happyending.consumer.config.KafkaJacksonConfig.Companion.createRewardAccumulatorJsonSerdes
import com.kakaopaysec.happyending.consumer.market.model.Purchase
import com.kakaopaysec.happyending.consumer.market.model.PurchasePattern
import com.kakaopaysec.happyending.consumer.market.model.RewardAccumulator
import com.kakaopaysec.happyending.consumer.market.partitioner.RewardStreamPartitioner
import com.kakaopaysec.happyending.consumer.market.transformer.PurchaseRewardTransformer
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.ValueTransformerSupplier
import org.apache.kafka.streams.state.Stores
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ZMarketStreamStateStoreListener {

    @Bean
    fun rewardingProcessor(streamsBuilder: StreamsBuilder): KStream<String, Purchase> {
        val purchaseJsonSerdes = createPurchaseJsonSerdes()

        val purchaseKStream = streamsBuilder.stream(
            "transactions",
            Consumed.with(Serdes.String(), Serdes.serdeFrom(purchaseJsonSerdes.first, purchaseJsonSerdes.second))
        )

        val purchasePatternSerdes = createPurchasePatternJsonSerdes()
        val patternKStream = purchaseKStream
            .mapValues { p: Purchase -> PurchasePattern.from(p) }

        patternKStream.print(Printed.toSysOut<String?, PurchasePattern?>().withLabel("patterns"))
        patternKStream.to(
            "patterns",
            Produced.with(Serdes.String(), Serdes.serdeFrom(purchasePatternSerdes.first, purchasePatternSerdes.second))
        )

        val rewardsStateStoreName = "rewardsPointsStore"
        val streamPartitioner = RewardStreamPartitioner()

        val storeSupplier = Stores.inMemoryKeyValueStore(rewardsStateStoreName)
        val storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.String(), Serdes.Integer())
        streamsBuilder.addStateStore(storeBuilder)

        val transByCustomerStream = purchaseKStream.through(
            "customer_transactions",
            Produced.with(
                Serdes.String(),
                Serdes.serdeFrom(purchaseJsonSerdes.first, purchaseJsonSerdes.second),
                streamPartitioner
            )
        )

        val statefulRewardAccumulator = transByCustomerStream.transformValues(
            ValueTransformerSupplier { PurchaseRewardTransformer(rewardsStateStoreName) },
            rewardsStateStoreName
        )

        val rewardAccumulatorSerdes = createRewardAccumulatorJsonSerdes()
        statefulRewardAccumulator.print(
            Printed.toSysOut<String?, RewardAccumulator?>().withLabel("rewards")
        )
        statefulRewardAccumulator.to(
            "rewards",
            Produced.with(Serdes.String(), Serdes.serdeFrom(rewardAccumulatorSerdes.first, rewardAccumulatorSerdes.second))
        )

        return purchaseKStream
    }
}
