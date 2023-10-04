package com.kakaopaysec.happyending.consumer.market.partitioner

import com.kakaopaysec.happyending.consumer.market.model.Purchase
import org.apache.kafka.streams.processor.StreamPartitioner

class RewardStreamPartitioner : StreamPartitioner<String, Purchase> {

    override fun partition(topic: String, key: String?, value: Purchase, numPartitions: Int): Int {
        return value.customerId.hashCode() % numPartitions
    }
}
