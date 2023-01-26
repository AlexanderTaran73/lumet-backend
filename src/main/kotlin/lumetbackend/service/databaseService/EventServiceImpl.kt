package lumetbackend.service.databaseService

import lumetbackend.entities.EventEntity
import lumetbackend.repositories.EventRepository
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional

@Service
@Transactional
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

    override fun deleteById(id: Int) {
        eventRepository.deleteById(id)
    }
}