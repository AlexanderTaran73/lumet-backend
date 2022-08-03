package lumetbackend.service.databaseService

import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.repositories.RegistrationDataRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class RegistrationDataServiceImpl(private val registrationDataRepository: RegistrationDataRepository): RegistrationDataService {
    override fun findAll(): List<RegistrationDataEntity> {
        return registrationDataRepository.findAll()
    }

    override fun findByEmail(email: String): RegistrationDataEntity? {
        return registrationDataRepository.findByEmail(email)
    }

    override fun deleteByEmail(email: String) {
        registrationDataRepository.deleteByEmail(email)
    }

    override fun save(registrationDataEntity: RegistrationDataEntity) {
        registrationDataRepository.save(registrationDataEntity)
    }
}