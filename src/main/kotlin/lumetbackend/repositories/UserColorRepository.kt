package lumetbackend.repositories


import lumetbackend.entities.UserColor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface UserColorRepository : JpaRepository<UserColor, Int>{
    fun findByName(name : String) : UserColor
}