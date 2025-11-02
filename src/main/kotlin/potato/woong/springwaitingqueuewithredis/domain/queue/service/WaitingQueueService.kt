package potato.woong.springwaitingqueuewithredis.domain.queue.service

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import potato.woong.springwaitingqueuewithredis.domain.queue.dto.api.response.WaitingQueueResponse
import potato.woong.springwaitingqueuewithredis.settings.exception.CustomException
import potato.woong.springwaitingqueuewithredis.settings.exception.ErrorCode
import potato.woong.springwaitingqueuewithredis.settings.utils.SecurityUtils

@Service
class WaitingQueueService(
    private val redisTemplate: RedisTemplate<String, Any>
) {

    fun joinQueue(): WaitingQueueResponse {
        val userId = SecurityUtils.getCurrentUserId()

        // 이미 대기열에 있으면 기존 순번 반환
        val existingRank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, userId)
        if (existingRank != null) {
            return WaitingQueueResponse.createWaiting(existingRank + 1)
        }

        // 대기열에 사용자 추가
        redisTemplate.opsForZSet().add(WAITING_QUEUE_KEY, userId, System.currentTimeMillis().toDouble())
        val rank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, userId) ?: throw CustomException(ErrorCode.FAIL_TO_JOIN_QUEUE)

        return WaitingQueueResponse.createWaiting(rank + 1)
    }

    fun getQueueStatus(): WaitingQueueResponse {
        val userId = SecurityUtils.getCurrentUserId()

        // 이미 입장이 허가된 사용자인지 확인
        val isActive = redisTemplate.hasKey("$ACTIVE_KEY:$userId")
        if (isActive) {
            return WaitingQueueResponse.createActive()
        }

        // 대기열 내 순번 확인
        val rank = redisTemplate.opsForZSet().rank(WAITING_QUEUE_KEY, userId) ?: throw CustomException(ErrorCode.NOT_IN_QUEUE)
        return WaitingQueueResponse.createWaiting(rank + 1)
    }

    companion object {
        private const val WAITING_QUEUE_KEY = "queue:waiting"
        private const val ACTIVE_KEY = "active"
    }
}