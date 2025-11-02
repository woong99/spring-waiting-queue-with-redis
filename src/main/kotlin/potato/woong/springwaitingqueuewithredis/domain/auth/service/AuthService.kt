package potato.woong.springwaitingqueuewithredis.domain.auth.service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import potato.woong.springwaitingqueuewithredis.domain.auth.dto.api.response.LoginResponse
import potato.woong.springwaitingqueuewithredis.domain.auth.dto.command.LoginCommand
import potato.woong.springwaitingqueuewithredis.domain.auth.entity.User
import potato.woong.springwaitingqueuewithredis.domain.auth.repository.UserRepository
import potato.woong.springwaitingqueuewithredis.settings.exception.CustomException
import potato.woong.springwaitingqueuewithredis.settings.exception.ErrorCode
import potato.woong.springwaitingqueuewithredis.settings.security.components.JwtTokenProvider

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Transactional(readOnly = true)
    fun login(
        command: LoginCommand,
    ): LoginResponse {
        val user = getValidUser(command)

        // Authentication Token 생성
        val authentication = UsernamePasswordAuthenticationToken(
            user.userId,
            user.password,
        )

        // Jwt Token 발급
        val tokenDto = jwtTokenProvider.generateToken(authentication)
        return LoginResponse.from(user, tokenDto.accessToken)
    }

    /**
     * 로그인 시 사용자 정보 검증
     */
    private fun getValidUser(
        command: LoginCommand,
    ): User {
        // 사용자 정보 조회
        val user = userRepository.findByUserId(command.userId)
            ?: throw CustomException(ErrorCode.INVALID_ID_OR_PASSWORD)

        // 비밀번호 검증
        if (!passwordEncoder.matches(command.password, user.password)) {
            throw CustomException(ErrorCode.INVALID_ID_OR_PASSWORD)
        }

        return user
    }
}