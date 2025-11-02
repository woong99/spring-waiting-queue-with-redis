package potato.woong.springwaitingqueuewithredis.settings.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils
import potato.woong.springwaitingqueuewithredis.settings.security.components.JwtAccessDeniedHandler
import potato.woong.springwaitingqueuewithredis.settings.security.components.JwtAuthenticationEntryPoint
import potato.woong.springwaitingqueuewithredis.settings.security.components.JwtTokenProvider
import potato.woong.springwaitingqueuewithredis.settings.security.filter.JwtAuthenticationFilter

@Configuration
@EnableMethodSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jwtAuthenticationEntryPoint: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            // Basic Auth 비활성화
            httpBasic { disable() }

            // CSRF 비활성화
            csrf { disable() }

            cors {}

            formLogin { disable() }

            // Session 비활성화
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }

            // 예외 처리 설정
            exceptionHandling {
                authenticationEntryPoint = jwtAuthenticationEntryPoint
                accessDeniedHandler = jwtAccessDeniedHandler
            }

            // EndPoint 접근 권한 설정
            authorizeHttpRequests {
                // Preflight 요청 허용(CORS 설정)
                authorize(CorsUtils::isPreFlightRequest, permitAll)

                PERMIT_ALL.forEach { authorize(it, permitAll) }

                authorize(anyRequest, authenticated)
            }

            // JwtAuthentication Filter 설정
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                JwtAuthenticationFilter(jwtTokenProvider)
            )
        }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    companion object {
        private val PERMIT_ALL = arrayOf(
            "/api/v1/auth/login"
        )
    }
}