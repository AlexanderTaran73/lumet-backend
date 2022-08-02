package lumetbackend.repositories

import lumetbackend.entities.RegistrationDataEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface RegistrationDataRepository : JpaRepository<RegistrationDataEntity, Int> {

    fun findByEmail(email : String): RegistrationDataEntity?
    fun deleteByEmail(email : String)

}