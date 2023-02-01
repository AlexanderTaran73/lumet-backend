package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.controller.CreateEventRequest
import lumetbackend.controller.EventDTO
import lumetbackend.entities.EventEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.EventService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.imageservice.service.FileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@Service
class EventChangesService(private val jwtProvider: JwtProvider,
                          private val userService: UserService,
                          private val arrayService: ArrayService,
                          private val eventService: EventService,
                          private val fileService: FileService,) {



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

    fun removeEvent(request: HttpServletRequest, eventId: Int): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!

        if (user.userEvents!!.createdEvents.contains(eventId)){
            user.userEvents!!.createdEvents = arrayService.removeInt(user.userEvents!!.createdEvents, eventId)

            userService.save(user)

            eventService.deleteById(eventId)
            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.CONFLICT)
    }

    fun changeEvent(createEventRequest: CreateEventRequest, request: HttpServletRequest, eventId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        if (user.userEvents!!.createdEvents.contains(eventId)){
            try {
                val eventEntity = eventService.findById(eventId).get()
                eventEntity.name = createEventRequest.name
                eventEntity.description = createEventRequest.description
                eventEntity.userrating = user.ratingid!!.rating
                eventEntity.hobbytype = createEventRequest.hobbytype
                eventEntity.time = createEventRequest.time
                eventEntity.desiredage = createEventRequest.desiredage
                eventEntity.participantLimit = createEventRequest.participantLimit
                eventEntity.participantsAnonymity = createEventRequest.participantsAnonymity
                eventEntity.privacyStatus = createEventRequest.privacyStatus
                eventEntity.registrationSettings = createEventRequest.registrationSettings
                eventEntity.latitude = createEventRequest.latitude
                eventEntity.longitude = createEventRequest.longitude
                
                eventService.save(eventEntity)
            }
            catch (_:Exception){
                user.userEvents!!.createdEvents = arrayService.removeInt(user.userEvents!!.createdEvents, eventId)
                userService.save(user)
            }


            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.CONFLICT)
    }


    fun getAllEvents(request: HttpServletRequest): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val eventList = eventService.findAll()

        val eventDTO = mutableListOf<EventDTO>()
        for (i in eventList){
            val e = eventToEventDTO(i)
            if (i.participantsAnonymity == "True" && !i.confirmedParticipants.contains(user.id!!)) e.confirmedParticipants=null

            if(i.privacyStatus == "True" && !i.confirmedParticipants.contains(user.id!!)){
                e.longitude = null
                e.latitude = null
            }
            eventDTO.add(e)
        }

        return ResponseEntity(eventDTO, HttpStatus.OK)
    }

    fun getAllEventsSort(request: HttpServletRequest, search: String, minAge: Int, rating: Int, hobby: String, usersLimit: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val eventList = eventService.findAll()

        val eventDTO = mutableListOf<EventDTO>()
        for (i in eventList){
            val e = eventToEventDTO(i)
            if (i.participantsAnonymity == "True" && !i.confirmedParticipants.contains(user.id!!)) e.confirmedParticipants=null

            if(i.privacyStatus == "True" && !i.confirmedParticipants.contains(user.id!!)){
                e.longitude = null
                e.latitude = null
            }
            eventDTO.add(e)
        }

        val sortedEventDTO = mutableListOf<EventDTO>()
        for(i in eventDTO){
            if(search=="EMPTY_SEARCH" || (i.name!!.contains(search, ignoreCase = true) || i.description!!.contains(search, ignoreCase = true))){
                if (i.desiredage!! >=minAge && i.userrating!! >=rating && i.participantLimit!!>= usersLimit &&(hobby=="ALL" || i.hobbytype!!.contains(hobby, ignoreCase = true))) {
                    sortedEventDTO.add(i)
                }

            }
        }

        return ResponseEntity(sortedEventDTO, HttpStatus.OK)
    }

    fun getEventsById(request: HttpServletRequest, eventIdList: List<Int>): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!


        val eventDTO = mutableListOf<EventDTO>()
        for (j in eventIdList){
            try {
                val i = eventService.findById(j).get()
                val e = eventToEventDTO(i)
                if (i.participantsAnonymity == "True" && !i.confirmedParticipants.contains(user.id!!)) e.confirmedParticipants=null

                if(i.privacyStatus == "True" && !i.confirmedParticipants.contains(user.id!!)){
                    e.longitude = null
                    e.latitude = null
                }
                eventDTO.add(e)
            }
            catch (_:Exception){}

        }

        return ResponseEntity(eventDTO, HttpStatus.OK)
    }

    fun getEventById(request: HttpServletRequest, eventId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val i = eventService.findById(eventId).get()
        val e = eventToEventDTO(i)
        if (i.participantsAnonymity == "True" && !i.confirmedParticipants.contains(user.id!!)) e.confirmedParticipants=null

        if(i.privacyStatus == "True" && !i.confirmedParticipants.contains(user.id!!)){
            e.longitude = null
            e.latitude = null
        }
        return ResponseEntity(e, HttpStatus.OK)
    }



    fun eventToEventDTO(eventEntity: EventEntity): EventDTO{
        return EventDTO(
                eventEntity.id!!,
                eventEntity.userrating,
                eventEntity.userid,
                eventEntity.name,
                eventEntity.description,
                eventEntity.hobbytype,
                eventEntity.avatarimage,
                eventEntity.time,
                eventEntity.desiredage,
                eventEntity.participantLimit,
                eventEntity.participantsAnonymity,
                eventEntity.privacyStatus,
                eventEntity.registrationSettings,
                eventEntity.confirmedParticipants,
                eventEntity.latitude,
                eventEntity.longitude
        )
    }




    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }

    fun getCreaterEvents(request: HttpServletRequest): ResponseEntity<Any> {
        val user = getUserByRequest(request)

        val eventDTO = mutableListOf<EventEntity>()
        for(i in user!!.userEvents!!.createdEvents){
            try {
                eventDTO.add(eventService.findById(i).get())
            }catch (_:Exception){}

        }
        return ResponseEntity(eventDTO, HttpStatus.OK)
    }

    fun changeImage(request: HttpServletRequest, eventId: Int, imageFile: MultipartFile): ResponseEntity<Any> {
        val name = fileService.saveImage(imageFile)

        val eventEntity = eventService.findById(eventId).get()

        eventEntity.avatarimage = name
        eventService.save(eventEntity)

        return ResponseEntity(HttpStatus.OK)
    }




}