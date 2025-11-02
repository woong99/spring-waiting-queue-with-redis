package potato.woong.springwaitingqueuewithredis.domain.product.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import potato.woong.springwaitingqueuewithredis.domain.product.entity.Product

interface ProductRepository : JpaRepository<Product, Long> {
    
    fun findByNameContaining(keyword: String, pageable: Pageable): Page<Product>
}