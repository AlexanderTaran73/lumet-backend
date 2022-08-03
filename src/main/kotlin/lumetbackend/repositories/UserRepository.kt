package lumetbackend.repositories


import lumetbackend.entities.RoleEntity
import lumetbackend.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserRepository : JpaRepository<UserEntity, Int>{


    fun findByEmail(email : String): UserEntity?
    fun findAllByLogin(login : String): List<UserEntity>
    fun deleteByEmail(email : String)




}