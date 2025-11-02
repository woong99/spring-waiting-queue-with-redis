package potato.woong.springwaitingqueuewithredis.settings.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.resource.NoResourceFoundException
import potato.woong.springwaitingqueuewithredis.settings.common.response.ApiResponse


@RestControllerAdvice
class GlobalExceptionHandler {

    private val log = KotlinLogging.logger {}

    /**
     * Handle CustomException
     */
    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ApiResponse<Unit>> {
        return ApiResponse.error(e.errorCode)
    }

    /**
     * Handle NoResourceFoundException
     */
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ResponseEntity<ApiResponse<Unit>> {
        return ApiResponse.error(ErrorCode.NOT_FOUND)
    }

    /**
     * Handle HttpRequestMethodNotSupportedException
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ApiResponse<Unit>> {
        log.error(e) { "HttpRequestMethodNotSupportedException" }
        return ApiResponse.error(ErrorCode.NOT_FOUND)
    }

    /**
     * Handle MethodArgumentNotValidException
     */
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ApiResponse<Unit>> {
        val firstError = e.bindingResult.allErrors.firstOrNull()

        // TypeMismatch인 경우 별도의 에러 메시지 반환
        val isTypeMismatch = firstError is FieldError && firstError.code == "typeMismatch"
        if (isTypeMismatch) {
            log.error(e) { "TypeMismatch" }
            return ApiResponse.fail(ErrorCode.INVALID_PARAMETER.message)
        }

        return ApiResponse.fail(e.bindingResult.allErrors[0].defaultMessage)
    }

    /**
     * Handle Exception
     */
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ApiResponse<Unit>> {

        log.error(e) { e.message ?: "Exception" }
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR)
    }
}