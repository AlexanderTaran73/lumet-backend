package lumetbackend.service.databaseService

import lumetbackend.entities.UserEntity
import java.util.*

interface UserService {
    fun findById(id : Int): Optional<UserEntity>

    fun findAll(): List<UserEntity>

    fun findByEmail(email : String): UserEntity?

    fun findAllByLogin(login : String): List<UserEntity>

    fun findByEmailAndPassword(email: String, password:String): UserEntity?

    fun deleteByEmail(email : String)

    fun save(userEntity: UserEntity)

    fun firstsave(userEntity: UserEntity)
}