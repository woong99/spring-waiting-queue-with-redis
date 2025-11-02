package potato.woong.springwaitingqueuewithredis.domain.auth.repository

import org.springframework.data.jpa.repository.JpaRepository
import potato.woong.springwaitingqueuewithredis.domain.auth.entity.User

interface UserRepository : JpaRepository<User, Long> {

    fun findByUserId(userId: String): User?
}