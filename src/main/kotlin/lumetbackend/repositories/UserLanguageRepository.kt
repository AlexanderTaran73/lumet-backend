package lumetbackend.repositories

import lumetbackend.entities.UserLanguage
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserLanguageRepository : JpaRepository<UserLanguage, Int> {

    fun findByName(name : String): UserLanguage
}