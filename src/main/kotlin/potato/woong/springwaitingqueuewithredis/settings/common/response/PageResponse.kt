package potato.woong.springwaitingqueuewithredis.settings.common.response

import org.springframework.data.domain.Page


data class PageResponse<T>(
    val results: List<T>,
    val totalCount: Long,
    val page: Long,
    val totalPage: Int,
    val pageSize: Long,
    val hasNextPage: Boolean
) {
    companion object {
        fun <T> from(
            page: Page<T>
        ): PageResponse<T> {
            return PageResponse(
                results = page.content,
                totalCount = page.totalElements,
                page = page.number.toLong(),
                totalPage = page.totalPages,
                pageSize = page.size.toLong(),
                hasNextPage = page.hasNext()
            )
        }
    }
}