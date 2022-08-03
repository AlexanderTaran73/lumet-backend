package lumetbackend.service

import lumetbackend.entities.EventEntity
import lumetbackend.repositories.EventRepository
import java.util.*

class EventServiceImpl(private val eventRepository: EventRepository): EventService {
    override fun findById(id: Int): Optional<EventEntity> {
        return eventRepository.findById(id)
    }

    override fun findAllByUserid(id: Int): List<EventEntity> {
        return eventRepository.findAllByUserid(id)
    }

    override fun findAllByHobbytype(hobbytype: String): List<EventEntity> {
        return eventRepository.findAllByHobbytype(hobbytype)
    }

    override fun deleteByIdAndUserid(id: Int, userid:Int){
        eventRepository.deleteByIdAndUserid(id, userid)
    }

    override fun save(eventEntity: EventEntity) {
        eventRepository.save(eventEntity)
    }
}