package potato.woong.springwaitingqueuewithredis.domain.product.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import potato.woong.springwaitingqueuewithredis.domain.product.dto.response.ProductSearchResponse
import potato.woong.springwaitingqueuewithredis.domain.product.service.ProductService
import potato.woong.springwaitingqueuewithredis.settings.common.response.ApiResponse
import potato.woong.springwaitingqueuewithredis.settings.common.response.PageResponse

@RestController
@RequestMapping("/api/v1/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun searchProducts(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "") keyword: String
    ): ResponseEntity<ApiResponse<PageResponse<ProductSearchResponse>>> {
        return ApiResponse.success(productService.searchProducts(page, keyword))
    }
}