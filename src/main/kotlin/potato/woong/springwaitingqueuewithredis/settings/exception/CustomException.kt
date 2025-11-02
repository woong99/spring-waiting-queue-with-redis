package potato.woong.springwaitingqueuewithredis.settings.exception

class CustomException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.errorCode) {
}