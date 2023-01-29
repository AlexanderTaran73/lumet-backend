package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
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
class UsersInteractionService (private val jwtProvider: JwtProvider,
                               private val arrayService: ArrayService,
                               private val userService: UserService,
                               private val eventService: EventService){

//    Blacklist

    fun addToBlacklist(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val blacklist = user.blacklist
        if (user.friendsid!!.friendlist.contains(userId)) return ResponseEntity(HttpStatus.valueOf("Cannot be blacklisted while friend"))
        try {
            user.blacklist = arrayService.appendInt(blacklist, userId)
            val recipient = userService.findById(userId).get()

            user.friendsid!!.userRequests = arrayService.removeInt(user.friendsid!!.userRequests, userId.toInt())
            recipient.friendsid!!.requestsToUser = arrayService.removeInt(recipient.friendsid!!.requestsToUser, user.id!!)
            user.friendsid!!.requestsToUser = arrayService.removeInt(user.friendsid!!.requestsToUser, userId.toInt())
            recipient.friendsid!!.userRequests = arrayService.removeInt(recipient.friendsid!!.userRequests, user.id!!)

            userService.save(recipient)
            userService.save(user)
            return ResponseEntity(HttpStatus.OK)
        }catch (_: Exception){
            return ResponseEntity(HttpStatus.valueOf("No such user"))
        }

    }

    fun deleteFromeBlacklist(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val blacklist = user.blacklist
        try {
            user.blacklist = arrayService.removeInt(blacklist, userId)
        }catch (_: Exception){
            return ResponseEntity(HttpStatus.valueOf("NOT INT"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

//    Friends

    fun friendRequest(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        try {
            val recipient = userService.findById(userId).get()

            if(recipient.blacklist.contains(user.id!!)) return ResponseEntity(HttpStatus.valueOf("Sender blocked"))
            if(user.friendsid!!.userRequests.contains(userId)) return ResponseEntity(HttpStatus.valueOf("Already sent"))
            if(user.blacklist.contains(userId)) user.blacklist = arrayService.removeInt(user.blacklist, userId)
            user.friendsid!!.userRequests = arrayService.appendInt(user.friendsid!!.userRequests, userId)
            userService.save(user)

            recipient.friendsid!!.requestsToUser = arrayService.appendInt(recipient.friendsid!!.requestsToUser, user.id!!)
            userService.save(recipient)

            return ResponseEntity(HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.valueOf("No such user"))
        }
    }

    fun cancelFriendRequest(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        try {
            val recipient = userService.findById(userId).get()
            user.friendsid!!.userRequests = arrayService.removeInt(user.friendsid!!.userRequests, userId)
            userService.save(user)

            recipient.friendsid!!.requestsToUser = arrayService.removeInt(recipient.friendsid!!.requestsToUser, user.id!!)
            userService.save(recipient)

            return ResponseEntity(HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.valueOf("No such user"))
        }
    }

    fun acceptRequest(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        if (user.friendsid!!.requestsToUser.contains(userId)){
            try {
                val recipient = userService.findById(userId).get()

                user.friendsid!!.friendlist = arrayService.appendInt(user.friendsid!!.friendlist, userId)
                user.friendsid!!.requestsToUser = arrayService.removeInt(user.friendsid!!.requestsToUser, userId)
                userService.save(user)

                recipient.friendsid!!.friendlist = arrayService.appendInt(recipient.friendsid!!.friendlist, user.id!!)
                recipient.friendsid!!.userRequests = arrayService.removeInt(recipient.friendsid!!.userRequests, user.id!!)
                userService.save(recipient)

                return ResponseEntity(HttpStatus.OK)
            }catch (_:Exception){
                user.friendsid!!.requestsToUser = arrayService.removeInt(user.friendsid!!.requestsToUser, userId)
                userService.save(user)
                return ResponseEntity(HttpStatus.valueOf("No such user"))
            }
        }else return ResponseEntity(HttpStatus.valueOf("No such request"))
    }

    fun rejectRequest(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        if (user.friendsid!!.requestsToUser.contains(userId)){
            try {
                val recipient = userService.findById(userId).get()

                user.friendsid!!.requestsToUser = arrayService.removeInt(user.friendsid!!.requestsToUser, userId)
                userService.save(user)

                recipient.friendsid!!.userRequests = arrayService.removeInt(recipient.friendsid!!.userRequests, user.id!!)
                userService.save(recipient)

                return ResponseEntity(HttpStatus.OK)
            }catch (_:Exception){
                user.friendsid!!.requestsToUser = arrayService.removeInt(user.friendsid!!.requestsToUser, userId)
                userService.save(user)
                return ResponseEntity(HttpStatus.valueOf("No such user"))
            }
        }else return ResponseEntity(HttpStatus.valueOf("No such request"))
    }

    fun removeFromFriends(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        try {
            val recipient = userService.findById(userId).get()
            recipient.friendsid!!.friendlist = arrayService.removeInt(recipient.friendsid!!.friendlist, user.id!!)
            userService.save(recipient)
        }catch (_:Exception){}
        user.friendsid!!.friendlist = arrayService.removeInt(user.friendsid!!.friendlist, userId)
        userService.save(user)

        return ResponseEntity(HttpStatus.OK)
    }


//    Rating

    fun giveFeedback(request: HttpServletRequest, userId: Int, feedback: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        if (feedback<1 || feedback>10) return ResponseEntity(HttpStatus.valueOf("No such feedback"))
        try {
            val recipient = userService.findById(userId).get()
            if (recipient.ratingid!!.usersId.contains(user.id!!)){
                recipient.ratingid!!.ratingByUser[recipient.ratingid!!.usersId.indexOf(user.id!!)]=feedback
            }
            else{
                recipient.ratingid!!.ratingByUser = arrayService.appendInt(recipient.ratingid!!.ratingByUser, feedback)
                recipient.ratingid!!.usersId = arrayService.appendInt(recipient.ratingid!!.usersId, user.id!!)
            }
            recipient.ratingid!!.rating = recipient.ratingid!!.ratingByUser.sum()/recipient.ratingid!!.usersId.size

            userService.save(recipient)

            return ResponseEntity(HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.valueOf("No such user"))
        }
    }

    fun cancelFeedback(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        try {
            val recipient = userService.findById(userId).get()
            if (recipient.ratingid!!.usersId.contains(user.id!!)){
                recipient.ratingid!!.ratingByUser = arrayService.removeInt(recipient.ratingid!!.ratingByUser, recipient.ratingid!!.ratingByUser[recipient.ratingid!!.usersId.indexOf(user.id!!)])
            }
            else return ResponseEntity(HttpStatus.valueOf("No such feedback"))

            userService.save(recipient)

            return ResponseEntity(HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.valueOf("No such user"))
        }
    }

//    Events
    fun applyForEvent(request: HttpServletRequest, eventId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val eventEntity = eventService.findById(eventId).get()

        if (eventEntity.confirmedParticipants.contains(user.id)) return ResponseEntity(HttpStatus.valueOf("Already confirmed"))
        else if (eventEntity.unconfirmedParticipants.contains(user.id)) return ResponseEntity(HttpStatus.valueOf("Already apply"))
        else {

            if (eventEntity.registrationSettings == "True") eventEntity.unconfirmedParticipants = arrayService.appendInt(eventEntity.unconfirmedParticipants, user.id!!)

            else eventEntity.unconfirmedParticipants = arrayService.appendInt(eventEntity.confirmedParticipants, user.id!!)

            user.userEvents!!.participationEvents = arrayService.appendInt(user.userEvents!!.participationEvents, eventId)

            userService.save(user)
            eventService.save(eventEntity)

            return ResponseEntity(HttpStatus.OK)
        }
    }

    fun removeApplyForEvent(request: HttpServletRequest, eventId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        val eventEntity = eventService.findById(eventId).get()

        user.userEvents!!.participationEvents = arrayService.removeInt(user.userEvents!!.participationEvents, eventId)
        eventEntity.unconfirmedParticipants = arrayService.removeInt(eventEntity.confirmedParticipants, user.id!!)
        eventEntity.unconfirmedParticipants = arrayService.removeInt(eventEntity.unconfirmedParticipants, user.id!!)

        userService.save(user)
        eventService.save(eventEntity)

        return ResponseEntity(HttpStatus.OK)
    }


    fun confirmApplyForEvent(request: HttpServletRequest, eventId: Int, userId: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val eventEntity = eventService.findById(eventId).get()
        if (user.userEvents!!.createdEvents.contains(eventId)){
            eventEntity.confirmedParticipants = arrayService.appendInt(eventEntity.confirmedParticipants, userId)
            eventEntity.unconfirmedParticipants = arrayService.removeInt(eventEntity.unconfirmedParticipants, userId)

            eventService.save(eventEntity)

            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.FORBIDDEN)
    }



    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }



}