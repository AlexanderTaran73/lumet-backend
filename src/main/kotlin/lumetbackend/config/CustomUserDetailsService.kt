package lumetbackend.config

import lumetbackend.service.UserService
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService(private val userService: UserService) : UserDetailsService {

    override fun loadUserByUsername(email: String): CustomUserDetails {
        val userEntity = userService.findByEmail(email)
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity!!)
    }
}
