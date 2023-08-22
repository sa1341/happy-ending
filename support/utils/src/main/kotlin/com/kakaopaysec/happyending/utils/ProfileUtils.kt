package com.kakaopaysec.happyending.utils

import com.kakaopaysec.happyending.utils.Profile.Companion.REDIS_K8S_SVC_SENTINEL_PROFILES
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.EnumSet

@Component
class ProfileUtils(
    val environment: Environment
) {

    fun getActiveProfile(): Profile {
        return Profile.values().firstOrNull { phase ->
            environment.activeProfiles.firstOrNull {
                phase.name == it.uppercase()
            } != null
        } ?: Profile.LOCAL
    }

    fun isLocalProfile(): Boolean = Profile.LOCAL == getActiveProfile()
    fun isSentinelRedis() = getActiveProfile() in REDIS_K8S_SVC_SENTINEL_PROFILES
}

enum class Profile {
    LOCAL,
    DEV,
    SANDBOX,
    BETA,
    PRODUCTION;

    fun toSystemCode(): SystemCode {
        return when (this) {
            DEV -> SystemCode.DEVELOP // 개발
            SANDBOX -> SystemCode.TEST // 샌박
            BETA, PRODUCTION -> SystemCode.PRODUCT // 운영
            else -> SystemCode.LOCAL // 로컬
        }
    }

    companion object {
        val REDIS_K8S_SVC_SENTINEL_PROFILES: EnumSet<Profile> = EnumSet.of(DEV, SANDBOX, BETA, PRODUCTION)
    }
}
