package lumetbackend.repositories

import lumetbackend.entities.UserEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserEventRepository: JpaRepository<UserEvent, Int> {
}