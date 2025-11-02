package potato.woong.springwaitingqueuewithredis.domain.auth.dto.api.request

import jakarta.validation.constraints.NotBlank
import potato.woong.springwaitingqueuewithredis.domain.auth.dto.command.LoginCommand

data class LoginRequest(

    @field:NotBlank(message = "ID를 입력해주세요.")
    val userId: String?,

    @field:NotBlank(message = "비밀번호를 입력해주세요.")
    val password: String?,
) {
    fun toCommand() = LoginCommand(
        userId = this.userId!!,
        password = this.password!!,
    )
}