package potato.woong.springwaitingqueuewithredis.settings.common.response


import com.fasterxml.jackson.annotation.JsonInclude
import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import potato.woong.springwaitingqueuewithredis.settings.exception.ErrorCode
import java.time.LocalDateTime

data class ApiResponse<T>(
    val status: Status,
    val statusCode: Int,
    val data: T? = null,
    @field:JsonInclude(JsonInclude.Include.NON_NULL) val message: String? = null,
    @field:JsonInclude(JsonInclude.Include.NON_NULL) val errorCode: String? = null,
    @field:JsonInclude(JsonInclude.Include.NON_NULL) val errorName: String? = null,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val requestId: String?
) {

    companion object {

        /**
         * 성공 응답
         *
         * @param data 응답 데이터
         * @return ResponseEntity
         */
        fun <T> success(data: T): ResponseEntity<ApiResponse<T>> =
            ResponseEntity.ok(
                ApiResponse(
                    status = Status.SUCCESS,
                    statusCode = 200,
                    data = data,
                    requestId = MDC.get("requestId")
                )
            )

        /**
         * 성공 응답
         *
         * @return ResponseEntity
         */
        fun success(): ResponseEntity<ApiResponse<Unit>> =
            ResponseEntity.ok(
                ApiResponse(
                    status = Status.SUCCESS,
                    statusCode = 200,
                    requestId = MDC.get("requestId")
                )
            )

        /**
         * 실패 응답
         *
         * @param message 메시지
         * @return ResponseEntity
         */
        fun fail(message: String?): ResponseEntity<ApiResponse<Unit>> =
            ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                    ApiResponse(
                        status = Status.FAIL,
                        statusCode = 400,
                        message = message,
                        requestId = MDC.get("requestId")
                    )
                )

        /**
         * 에러 응답
         *
         * @param errorCode 에러 코드
         * @return ResponseEntity
         */
        fun error(errorCode: ErrorCode): ResponseEntity<ApiResponse<Unit>> =
            ResponseEntity
                .status(errorCode.httpStatus)
                .body(
                    ApiResponse(
                        status = Status.ERROR,
                        statusCode = errorCode.httpStatus.value(),
                        message = errorCode.message,
                        errorCode = errorCode.errorCode,
                        errorName = errorCode.name,
                        requestId = MDC.get("requestId")
                    )
                )
    }
}

enum class Status {
    SUCCESS,
    FAIL,
    ERROR
}