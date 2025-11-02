package potato.woong.springwaitingqueuewithredis.domain.queue.dto.api.response

import potato.woong.springwaitingqueuewithredis.domain.queue.enums.WaitingStatus

data class WaitingQueueResponse(
    val position: Long,
    val status: WaitingStatus
) {
    companion object {
        fun createWaiting(
            rank: Long
        ) = WaitingQueueResponse(
            position = rank,
            status = WaitingStatus.WAITING
        )

        fun createActive() = WaitingQueueResponse(
            position = 0,
            status = WaitingStatus.ACTIVE
        )
    }
}