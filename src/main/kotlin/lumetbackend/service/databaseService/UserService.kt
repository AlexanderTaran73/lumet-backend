package lumetbackend.service.databaseService

import lumetbackend.entities.*
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

    fun friendsSave(friends: Friends)
    fun userRatingSave(userRating: UserRating)
    fun userEventSave(userEvent: UserEvent)
    fun userPrivacystatusSave(userPrivacystatus: UserPrivacystatus)
    fun savePassword(userEntity: UserEntity)
}