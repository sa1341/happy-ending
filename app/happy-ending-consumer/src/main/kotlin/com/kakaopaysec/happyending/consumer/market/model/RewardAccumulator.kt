package com.kakaopaysec.happyending.consumer.market.model

data class RewardAccumulator(
    val customerId: String,
    val purchaseTotal: Double,
    val totalRewardPoints: Int,
    val currentRewardPoints: Int,
    val daysFromLastPurchase: Int = 0
) {
    companion object {
        fun from(purchase: Purchase): RewardAccumulator {
            val rewardPoints = purchase.price.times(purchase.quantity).toInt()
            return RewardAccumulator(
                customerId = "${purchase.lastName},${purchase.firstName}",
                purchaseTotal = purchase.price.times(purchase.quantity),
                currentRewardPoints = rewardPoints,
                totalRewardPoints = rewardPoints
            )
        }
    }
}
