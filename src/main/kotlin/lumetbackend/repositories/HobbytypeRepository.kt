package lumetbackend.repositories

import lumetbackend.entities.Hobbytype
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HobbytypeRepository : JpaRepository<Hobbytype, Int> {

    fun findByName(name: String): Hobbytype
}