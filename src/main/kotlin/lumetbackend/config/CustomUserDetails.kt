package lumetbackend.config

import lumetbackend.entities.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails : UserDetails {
    private var login: String? = null
    private var password: String? = null
    private var grantedAuthorities: Collection<GrantedAuthority?>? = null
    override fun getAuthorities(): Collection<GrantedAuthority?> {
        return grantedAuthorities!!
    }

    override fun getPassword(): String {
        return password!!
    }

    override fun getUsername(): String {
        return login!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun fromUserEntityToCustomUserDetails(userEntity: UserEntity): CustomUserDetails {
            val c = CustomUserDetails()
            c.login = userEntity.email
            c.password = userEntity.password
            c.grantedAuthorities = listOf(SimpleGrantedAuthority(userEntity.roleid!!.name))
            return c
        }
    }
}
