package lumetbackend.repositories

import lumetbackend.entities.Friends
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface FriendsRepository : JpaRepository<Friends, Int> {
    fun save(friends: Friends)
}