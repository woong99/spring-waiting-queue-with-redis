package potato.woong.springwaitingqueuewithredis.settings.security.dto

import java.time.LocalDateTime
import java.time.ZoneId


data class TokenDto(
    val accessToken: Token,
) {
    companion object {
        fun create(
            accessToken: Token,
        ) = TokenDto(
            accessToken = accessToken,
        )
    }

    data class Token(
        val token: String,
        val expiredAt: Long,
    ) {
        val calculatedExpiredAt: Long
            get() = (expiredAt / 1000) - LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000

        companion object {
            fun create(
                token: String,
                expiredAt: Long,
            ) = Token(
                token = token,
                expiredAt = expiredAt,
            )
        }
    }
}