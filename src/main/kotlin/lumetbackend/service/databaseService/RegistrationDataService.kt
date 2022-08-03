package lumetbackend.service.databaseService

import lumetbackend.entities.RegistrationDataEntity


interface RegistrationDataService {
    fun findAll(): List<RegistrationDataEntity>

    fun findByEmail(email : String): RegistrationDataEntity?

    fun deleteByEmail(email : String)

    fun save(registrationDataEntity: RegistrationDataEntity)
}