package lumetbackend.repositories

import lumetbackend.entities.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface EventRepository : JpaRepository<EventEntity, Int>{
    fun findAllByUserid(userid:Int):List<EventEntity>
    fun findAllByHobbytype(hobbytype:String):List<EventEntity>
    fun deleteByIdAndUserid(id: Int, userid:Int)
}