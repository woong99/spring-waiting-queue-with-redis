package potato.woong.springwaitingqueuewithredis.domain.auth.dto.api.response

import potato.woong.springwaitingqueuewithredis.domain.auth.entity.User
import potato.woong.springwaitingqueuewithredis.settings.security.dto.TokenDto

data class LoginResponse(
    val name: String,
    val accessToken: TokenDto.Token
) {
    companion object {
        fun from(
            user: User,
            accessToken: TokenDto.Token
        ) = LoginResponse(
            name = user.name,
            accessToken = accessToken
        )
    }
}
