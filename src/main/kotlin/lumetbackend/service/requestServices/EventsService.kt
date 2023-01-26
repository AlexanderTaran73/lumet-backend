package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.controller.CreateEventRequest
import lumetbackend.entities.EventEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.EventService
import lumetbackend.service.databaseService.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import javax.servlet.http.HttpServletRequest

@Service
class EventChangesService(private val jwtProvider: JwtProvider,
                            private val userService: UserService,
                            private val arrayService: ArrayService,
                            private val eventService: EventService) {



    fun createEvent(createEventRequest: CreateEventRequest, request: HttpServletRequest): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val eventEntity = EventEntity(
                user.ratingid!!.rating,
                user.id,
                createEventRequest.name,
                createEventRequest.description,
                createEventRequest.hobbytype,
                createEventRequest.time,
                createEventRequest.desiredage,
                createEventRequest.participantLimit,
                createEventRequest.participantsAnonymity,
                createEventRequest.privacyStatus,
                createEventRequest.registrationSettings,
                createEventRequest.latitude,
                createEventRequest.longitude)

        eventService.save(eventEntity)

        user.userEvents!!.createdEvents = arrayService.appendInt(user.userEvents!!.createdEvents, eventEntity.id!!)

        userService.save(user)

        return ResponseEntity(HttpStatus.CREATED)
    }

    fun removeEvent(request: HttpServletRequest, event_id: Int): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!

        if (user.userEvents!!.createdEvents.contains(event_id)){
            user.userEvents!!.createdEvents = arrayService.removeInt(user.userEvents!!.createdEvents, event_id)

            userService.save(user)

            eventService.deleteById(event_id)
            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.CONFLICT)
    }








    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }
}