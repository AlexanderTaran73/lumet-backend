package lumetbackend.service

import lumetbackend.entities.UserEntity
import lumetbackend.repositories.RoleRepository
import lumetbackend.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository, private val roleRepository: RoleRepository, private val passwordEncoder : PasswordEncoder): UserService {
    override fun findById(id: Int): Optional<UserEntity> {
        return userRepository.findById(id)
    }

    override fun findAll(): List<UserEntity> {
        return userRepository.findAll()
    }

    override fun findByEmail(email: String): UserEntity? {
        return userRepository.findByEmail(email)
    }

    override fun findAllByLogin(login: String): List<UserEntity> {
        return userRepository.findAllByLogin(login)
    }

    override fun deleteByEmail(email: String) {
        userRepository.deleteByEmail(email)
    }

    override fun save(userEntity: UserEntity) {
        userEntity.roleid = roleRepository.findByName("ROLE_USER")
        userEntity.password = passwordEncoder.encode(userEntity.password)
        userRepository.save(userEntity)
    }


    override fun findByEmailAndPassword(email: String, password: String): UserEntity? {
        val userEntity: UserEntity? = userRepository.findByEmail(email)
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.password)) {
                return userEntity
            }
        }
        return null
    }
}