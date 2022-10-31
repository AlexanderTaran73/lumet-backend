package lumetbackend.repositories

import lumetbackend.entities.AccountStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface AccountStatusRepository : JpaRepository<AccountStatus, Int>{

    fun findByName(name : String): AccountStatus
}