package potato.woong.springwaitingqueuewithredis.settings.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ISE", "서버 내부 오류입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FBD", "권한이 없습니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNA", "인증되지 않았습니다."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "BR", "잘못된 요청입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NF", "존재하지 않는 리소스입니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BR", "잘못된 요청입니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "IAT", "유효하지 않은 액세스 토큰입니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "EAT", "만료된 액세스 토큰입니다."),

    INVALID_ID_OR_PASSWORD(HttpStatus.UNAUTHORIZED, "ATH1", "아이디 또는 비밀번호가 올바르지 않습니다."),

    FAIL_TO_JOIN_QUEUE(HttpStatus.INTERNAL_SERVER_ERROR, "WTQ1", "대기열 참여에 실패했습니다."),
}