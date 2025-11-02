package potato.woong.springwaitingqueuewithredis.domain.auth.controller

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import potato.woong.springwaitingqueuewithredis.domain.auth.dto.api.request.LoginRequest
import potato.woong.springwaitingqueuewithredis.domain.auth.dto.api.response.LoginResponse
import potato.woong.springwaitingqueuewithredis.domain.auth.service.AuthService
import potato.woong.springwaitingqueuewithredis.settings.common.response.ApiResponse

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    /**
     * 로그인 API
     */
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginRequest,
    ): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.login(request.toCommand())

        return ApiResponse.success(response)
    }
}