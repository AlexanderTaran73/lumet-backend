package lumetbackend.service.databaseService

import lumetbackend.entities.EventEntity
import java.util.*


interface EventService {

    fun findById(id : Int): Optional<EventEntity>

    fun findAllByUserid(id:Int):List<EventEntity>

    fun findAllByHobbytype(hobbytype:String):List<EventEntity>

    fun deleteByIdAndUserid(id: Int, userid:Int)

    fun save(eventEntity: EventEntity)

    fun deleteById(id: Int)

    fun findAll():List<EventEntity>
}