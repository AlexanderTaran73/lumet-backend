package lumetbackend.controller

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.EventEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.EventService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.imageservice.service.FileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/events")
class EventsController(private val eventService: EventService, private val fileService: FileService, private val userService: UserService, private val jwtProvider: JwtProvider, private val arrayService: ArrayService) {


    @PostMapping("/create")
    fun createEvent(@Valid @RequestBody eventCreateRequest: EventCreateRequest, request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if (userEntity!=null){
            val eventEntity = EventEntity(userEntity.id, userEntity.rating, eventCreateRequest.name, eventCreateRequest.description, eventCreateRequest.hobbytype, eventCreateRequest.time, eventCreateRequest.coordinates, eventCreateRequest.desiredage, eventCreateRequest.participantLimit, eventCreateRequest.participantsAnonymity, eventCreateRequest.privacyStatus, eventCreateRequest.registrationSettings)
            eventService.save(eventEntity)
            userEntity.events = arrayService.appendInt(userEntity.events, eventEntity.id!!)
            return ResponseEntity(eventEntity, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    @PostMapping("/delete")
    fun deleteEvent(request: HttpServletRequest, @Valid @RequestBody eventID: String): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if (userEntity!=null) {
            try {
                val id = eventID.toInt()
                val userId = userEntity.id
                if (userId != null) {
                    eventService.deleteByIdAndUserid(id, userId)
                    userEntity.events = arrayService.removeInt(userEntity.events, id)
                    return ResponseEntity(HttpStatus.OK)
                }
            }catch (_:Exception){
                return ResponseEntity(HttpStatus.NOT_FOUND)
            }
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }







    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }
}

data class EventCreateRequest(
        val name: String?,
        val description: String?,
        val hobbytype: String?,
        val time: String?,
        val coordinates: String?,
        val desiredage: Int?,
        val participantLimit: Int?,
        val participantsAnonymity: String?,
        val privacyStatus: String?,
        val registrationSettings: String?
)