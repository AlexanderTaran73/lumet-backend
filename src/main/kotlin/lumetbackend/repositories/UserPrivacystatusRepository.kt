package lumetbackend.repositories

import lumetbackend.entities.UserPrivacystatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserPrivacystatusRepository : JpaRepository<UserPrivacystatus, Int>{
    fun findByName(name: String): UserPrivacystatus
}