package potato.woong.springwaitingqueuewithredis.domain.product.service

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import potato.woong.springwaitingqueuewithredis.domain.product.dto.response.ProductSearchResponse
import potato.woong.springwaitingqueuewithredis.domain.product.repository.ProductRepository
import potato.woong.springwaitingqueuewithredis.settings.common.response.PageResponse

@Service
class ProductService(
    private val productRepository: ProductRepository
) {

    @Transactional(readOnly = true)
    fun searchProducts(
        page: Int,
        keyword: String
    ): PageResponse<ProductSearchResponse> {
        val pageable = PageRequest.of(page, 10)
        val products = productRepository.findByNameContaining(keyword, pageable)

        return PageResponse.from(
            products.map { ProductSearchResponse.fromEntity(it) }
        )
    }
}