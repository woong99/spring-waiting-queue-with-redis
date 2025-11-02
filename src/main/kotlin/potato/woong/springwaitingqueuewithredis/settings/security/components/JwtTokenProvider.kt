package potato.woong.springwaitingqueuewithredis.settings.security.components

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import potato.woong.springwaitingqueuewithredis.settings.exception.CustomException
import potato.woong.springwaitingqueuewithredis.settings.exception.ErrorCode
import potato.woong.springwaitingqueuewithredis.settings.security.data.CustomUserDetails
import potato.woong.springwaitingqueuewithredis.settings.security.dto.TokenDto
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtTokenProvider(
    @param:Value("\${jwt.secret}") private val secretKey: String,
    @param:Value("\${jwt.expiration}") private val expiration: Long,
) {

    fun generateToken(
        authentication: Authentication,
    ) = TokenDto.create(
        accessToken = generateAccessToken(authentication),
    )

    /**
     * Access Token 생성
     */
    fun generateAccessToken(
        authentication: Authentication
    ): TokenDto.Token {
        val authorities = authentication.authorities.joinToString(",") { it.authority }
        val now = Date().time

        // Access Token 만료 시간 설정
        val accessTokenExpiresIn = Date(now + expiration)

        // Access Token 생성
        val accessToken = Jwts.builder()
            .subject(authentication.name)
            .claim("role", authorities)
            .expiration(accessTokenExpiresIn)
            .signWith(getSigningKey(), Jwts.SIG.HS256)
            .compact()

        return TokenDto.Token.create(
            token = accessToken,
            expiredAt = accessTokenExpiresIn.time
        )
    }

    /**
     * AccessToken의 유효성 검증
     */
    fun validateToken(
        accessToken: String
    ): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(accessToken)
            true
        } catch (e: SecurityException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: MalformedJwtException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: UnsupportedJwtException) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        } catch (e: ExpiredJwtException) {
            throw CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN)
        }
    }

    /**
     * Access Token을 파싱하여 Authentication 객체를 반환
     */
    fun getAuthentication(
        accessToken: String
    ): Authentication {
        // 토큰 복호화
        val claims = parseClaims(accessToken)
        if (claims["role"] == null) {
            throw CustomException(ErrorCode.INVALID_ACCESS_TOKEN)
        }

        // 권한정보 획득
        val authorities = claims["role"].toString()
            .split(",")
            .map {
                SimpleGrantedAuthority("ROLE_$it") // TODO : ROLE_이 필요한데 여기서 붙이는게 좋을지..
            }

        val userId = claims.subject
        val principal = CustomUserDetails(
            userId,
            authorities
        )
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * AccessToken을 파싱하여 Claims를 반환하는 메소드
     */
    private fun parseClaims(
        accessToken: String
    ): Claims {
        return try {
            Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(accessToken)
                .payload
        } catch (e: ExpiredJwtException) {
            throw CustomException(ErrorCode.EXPIRED_ACCESS_TOKEN)
        }
    }

    /**
     * SecretKey를 생성하는 메소드
     */
    private fun getSigningKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)

        return Keys.hmacShaKeyFor(keyBytes)!!
    }
}