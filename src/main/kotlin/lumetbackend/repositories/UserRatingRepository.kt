package lumetbackend.repositories

import lumetbackend.entities.UserRating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRatingRepository : JpaRepository<UserRating, Int> {
}