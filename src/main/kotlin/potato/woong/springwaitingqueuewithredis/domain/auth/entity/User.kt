package potato.woong.springwaitingqueuewithredis.domain.auth.entity

import jakarta.persistence.*

@Entity
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(unique = true, nullable = false)
    val userId: String,

    @Column(nullable = false)
    val password: String,

    @Column(nullable = false)
    val name: String
) {
}