package potato.woong.springwaitingqueuewithredis.domain.auth.dto.command

data class LoginCommand(
    val userId: String,
    val password: String,
) {
}