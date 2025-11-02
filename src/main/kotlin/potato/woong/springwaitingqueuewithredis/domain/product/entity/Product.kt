package potato.woong.springwaitingqueuewithredis.domain.product.entity

import jakarta.persistence.*

@Entity
class Product(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val price: Int,

    @Column(nullable = false)
    val stock: Int,

    @Column(nullable = false)
    val imageUrl: String
) {
}