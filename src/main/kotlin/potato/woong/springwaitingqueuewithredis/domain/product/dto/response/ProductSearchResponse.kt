package potato.woong.springwaitingqueuewithredis.domain.product.dto.response

import potato.woong.springwaitingqueuewithredis.domain.product.entity.Product

data class ProductSearchResponse(
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val stock: Int,
    val imageUrl: String
) {
    companion object {
        fun fromEntity(
            product: Product
        ) = ProductSearchResponse(
            id = product.id!!,
            name = product.name,
            description = product.description,
            price = product.price,
            stock = product.stock,
            imageUrl = product.imageUrl
        )
    }
}
