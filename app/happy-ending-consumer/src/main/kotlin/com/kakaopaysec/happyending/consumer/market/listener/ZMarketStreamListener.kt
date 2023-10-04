package com.kakaopaysec.happyending.consumer.market.listener

import com.kakaopaysec.happyending.consumer.market.model.Purchase
import com.kakaopaysec.happyending.consumer.market.model.PurchasePattern
import com.kakaopaysec.happyending.consumer.market.model.RewardAccumulator
import com.kakaopaysec.happyending.consumer.market.partitioner.RewardStreamPartitioner
import com.kakaopaysec.happyending.consumer.market.transformer.PurchaseRewardTransformer
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.Branched
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Printed.toSysOut
import org.apache.kafka.streams.kstream.Produced
import org.apache.kafka.streams.kstream.Repartitioned
import org.apache.kafka.streams.kstream.ValueTransformerSupplier
import org.apache.kafka.streams.state.Stores
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
        }.filter { key, purchase ->
            purchase.price > 5.00
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

        val streamBuilder = StreamsBuilder()
        val rewardsStateStoreName = "rewardsPointsStore"
        val streamPartitioner = RewardStreamPartitioner()

        val storeSupplier = Stores.inMemoryKeyValueStore(rewardsStateStoreName)
        val storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, Serdes.String(), Serdes.Integer())
        streamBuilder.addStateStore(storeBuilder)

        val transByCustomerStream = purchaseKStream.repartition(
            Repartitioned
                .streamPartitioner(streamPartitioner)
                .withKeySerde(Serdes.String())
                .withValueSerde(Serdes.serdeFrom(purchaseJsonSerdes.first, purchaseJsonSerdes.second))
                .withName("customer_transactions")
        )

        val statefulRewardAccumulator = transByCustomerStream.transformValues(
            ValueTransformerSupplier { PurchaseRewardTransformer(rewardsStateStoreName) },
            rewardsStateStoreName
        )

        statefulRewardAccumulator.print(
            toSysOut<String?, RewardAccumulator?>().withLabel("rewards")
        )
        statefulRewardAccumulator.to(
            "rewards",
            Produced.with(Serdes.String(), Serdes.serdeFrom(rewardAccumulatorSerdes.first, rewardAccumulatorSerdes.second))
        )

        // KStream에서 제공하느 branch를 사용하여 스트림별로 관심사 토픽으로 분기처리 적용
        divideCategoryStream(purchaseKStream, purchaseJsonSerdes)

        return purchaseKStream
    }

    private fun divideCategoryStream(
        purchaseKStream: KStream<String, Purchase>,
        jsonSerdes: Pair<JsonSerializer<Purchase>, JsonDeserializer<Purchase>>
    ) {
        val isCoffee = { key: String?, p: Purchase -> p.department == "coffee" }
        val isElectronics = { key: String?, p: Purchase -> p.department == "electronics" }

        val kStreamByDept = purchaseKStream
            .split()
            .branch(
                isCoffee,
                Branched.withConsumer { ks ->
                    ks.to(
                        "coffee",
                        Produced.with(Serdes.String(), Serdes.serdeFrom(jsonSerdes.first, jsonSerdes.second))
                    )
                }
            )
            .branch(
                isElectronics,
                Branched.withConsumer { ks ->
                    ks.to(
                        "electronics",
                        Produced.with(Serdes.String(), Serdes.serdeFrom(jsonSerdes.first, jsonSerdes.second))
                    )
                }
            )
            .noDefaultBranch()
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
