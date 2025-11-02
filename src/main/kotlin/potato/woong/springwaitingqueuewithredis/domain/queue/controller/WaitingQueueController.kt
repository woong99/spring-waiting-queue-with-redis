package potato.woong.springwaitingqueuewithredis.domain.queue.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import potato.woong.springwaitingqueuewithredis.domain.queue.dto.api.response.WaitingQueueResponse
import potato.woong.springwaitingqueuewithredis.domain.queue.service.WaitingQueueService
import potato.woong.springwaitingqueuewithredis.settings.common.response.ApiResponse

@RestController
@RequestMapping("/api/v1/waiting-queue")
class WaitingQueueController(
    private val waitingQueueService: WaitingQueueService
) {

    /**
     * 대기열 등록 API
     */
    @PostMapping("/join")
    fun joinQueue(): ResponseEntity<ApiResponse<WaitingQueueResponse>> {
        val response = waitingQueueService.joinQueue()

        return ApiResponse.success(response)
    }

    /**
     * 대기열 상태 조회 API
     */
    @GetMapping("/status")
    fun getQueueStatus(): ResponseEntity<ApiResponse<WaitingQueueResponse>> {
        val response = waitingQueueService.getQueueStatus()

        return ApiResponse.success(response)
    }
}