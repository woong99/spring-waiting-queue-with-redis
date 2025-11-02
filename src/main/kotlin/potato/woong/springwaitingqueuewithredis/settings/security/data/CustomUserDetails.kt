package potato.woong.springwaitingqueuewithredis.settings.security.data

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    val id: String,
    val authorities: List<GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities.toMutableList()
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return id
    }
}