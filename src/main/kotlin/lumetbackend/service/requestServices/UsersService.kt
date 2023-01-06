package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider

import lumetbackend.entities.DTO.UserDTO
import lumetbackend.entities.EventEntity
import lumetbackend.entities.Friends
import lumetbackend.entities.UserEntity
import lumetbackend.repositories.*
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.EventService
import lumetbackend.service.databaseService.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import java.util.NoSuchElementException
import javax.servlet.http.HttpServletRequest

@Service
class UsersService(private val jwtProvider: JwtProvider,
                   private val userService: UserService,
                   private val arrayService: ArrayService) {

    fun getUser(request: HttpServletRequest): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        if (userEntity.accountStatusid!!.name=="ACTIVE") return ResponseEntity(userToPrivateUserDTO(userEntity), HttpStatus.OK)
        else if (userEntity.accountStatusid!!.name=="REMOVED") return ResponseEntity(HttpStatus.valueOf("REMOVED"))
        else return ResponseEntity(HttpStatus.valueOf("BANNED or smth else"))
    }

    fun getUserById(request: HttpServletRequest, userId: Int): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val user = userService.findById(userId).get()
            var userDTO: UserDTO? = null

            if (user.id!=userEntity.id) {
                if (user.accountStatusid!!.name == "ACTIVE") {
                    if (!user.blacklist.contains(userEntity.id)) {
                        if (user.privacystatusid!!.profile == "ALL") {
                            userDTO = userToUserDTO(user)

                        } else if (user.privacystatusid!!.profile == "FRIENDS") {
                            if (user.friendsid!!.friendlist.contains(userEntity.id)) {
                                userDTO = userToUserDTO(user)
                            } else {
                                userDTO = userToClosedUserDTO(user)
                            }
                        } else if (user.privacystatusid!!.profile == "NOBODY") {
                            userDTO = userToClosedUserDTO(user)
                        }
                    }
                }
            }
            return ResponseEntity(userDTO, HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    fun getUserListById(request: HttpServletRequest, userIdList: List<Int>): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)?: return ResponseEntity(HttpStatus.NOT_FOUND)

        val userDTO = mutableListOf<UserDTO>()
        for (id in userIdList){
            try {
                val user = userService.findById(id).get()
                if (user.id!=userEntity.id) {
                    if (user.accountStatusid!!.name == "ACTIVE") {
                        if (!user.blacklist.contains(userEntity.id)) {
                            if (user.privacystatusid!!.profile == "ALL") {
                                userDTO.add(userToUserDTO(user))

                            } else if (user.privacystatusid!!.profile == "FRIENDS") {
                                if (user.friendsid!!.friendlist.contains(userEntity.id)) {
                                    userDTO.add(userToUserDTO(user))
                                }
                                else{
                                    userDTO.add(userToClosedUserDTO(user))
                                }
                            } else if (user.privacystatusid!!.profile == "NOBODY") {
                                userDTO.add(userToClosedUserDTO(user))
                            }
                        }
                    }
                }else{
                    if (user.accountStatusid!!.name == "ACTIVE"){
                        userDTO.add(userToPrivateUserDTO(user))
                    }
                }
            }catch (_: NoSuchElementException) { }
        }
        return ResponseEntity(userDTO, HttpStatus.OK)
    }


    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if(userEntity!=null) {
            val userEntityList = userService.findAll()

            val userDTO = mutableListOf<UserDTO>()
            for (user in userEntityList) {
                if (user.id!=userEntity.id) {
                    if (user.accountStatusid!!.name == "ACTIVE") {
                        if (!user.blacklist.contains(userEntity.id)) {
                            if (user.privacystatusid!!.profile == "ALL") {
                                userDTO.add(userToUserDTO(user))
                            } else if (user.privacystatusid!!.profile == "FRIENDS") {
                                if (user.friendsid!!.friendlist.contains(userEntity.id)) {
                                    userDTO.add(userToUserDTO(user))
                                }
                                else{
                                    userDTO.add(userToClosedUserDTO(user))
                                }
                            } else if (user.privacystatusid!!.profile == "NOBODY") {
                                userDTO.add(userToClosedUserDTO(user))
                            }
                        }
                    }
                }else{
                    if (user.accountStatusid!!.name == "ACTIVE"){
                        userDTO.add(userToPrivateUserDTO(user))
                    }
                }
            }
            return ResponseEntity(userDTO, HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    //    НЕ имеет смысла? аналогично getUserListById

//    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
//        val userEntity = getUserByRequest(request)
//        return if(userEntity!=null) {
//            var userFriends = userEntity.friendsid!!.friendlist
//
//            val userDTO = mutableListOf<UserDTO>()
//            for (id in userFriends){
//                try {
//                    val user = userService.findById(id).get()
//                    if (user.accountStatusid!!.name == "ACTIVE") {
//                        if (user.privacystatusid!!.profile == "NOBODY") {
//                            userDTO.add(userToClosedUserDTO(user))
//                        }else if(user.privacystatusid!!.profile == "FRIENDS" || user.privacystatusid!!.profile == "ALL"){
//                            userDTO.add(userToUserDTO(user))
//                        }
//                    }
//                } catch (_: NoSuchElementException) {
//                    userFriends = arrayService.removeInt(userFriends, id)
//                }
//            }
//            userEntity.friendsid!!.friendlist = userFriends
//            userService.save(userEntity)
//            ResponseEntity(userDTO, HttpStatus.OK)
//        }else ResponseEntity(HttpStatus.NOT_FOUND)
//    }

    fun getBlacklist(request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        return if(userEntity!=null) {
            var userBlacklist = userEntity.blacklist
            val userDTO = mutableListOf<UserDTO>()
            for (id in userEntity.blacklist){
                try {
                    val user = userService.findById(id).get()
                    if (user.accountStatusid!!.name=="ACTIVE")userDTO.add(userToBlacklistDTO(user))
//                    else userBlacklist = arrayService.removeInt(userBlacklist, id)
                } catch (_: NoSuchElementException) {
                    userBlacklist = arrayService.removeInt(userBlacklist, id)
                }
            }
            userEntity.blacklist = userBlacklist
            userService.save(userEntity)
            ResponseEntity(userDTO, HttpStatus.OK)
        }else ResponseEntity(HttpStatus.NOT_FOUND)
    }
//
//    fun getUserEvents(request: HttpServletRequest): ResponseEntity<Any>{
//        val userEntity = getUserByRequest(request)
//        if(userEntity!=null) {
//            var userEventsList = userEntity.events
//            val events = mutableListOf<EventEntity>()
//            for (id in userEntity.events){
//                try {
//                    val event = eventService.findById(id).get()
//                    events.add(event)
//                } catch (_: NoSuchElementException) {
//                    userEventsList = arrayService.removeInt(userEventsList, id)
//                }
//            }
//            userEntity.events = userEventsList
//            userService.save(userEntity)
//            return ResponseEntity(events, HttpStatus.OK)
//        }else return ResponseEntity(HttpStatus.NOT_FOUND)
//    }
//
//
    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }

    fun userToPrivateUserDTO(userEntity:UserEntity): UserDTO{
        val privateUserDTO = UserDTO(
                userEntity.id,
                userEntity.login,
                userEntity.email,
                userEntity.age,
                userEntity.avatarimage,
                userEntity.images,
                userEntity.blacklist,
                userEntity.ratingid!!.rating,
                userEntity.ratingid!!.ratingByUser.size,
                userEntity.privacystatusid!!.profile,
                userEntity.privacystatusid!!.map,
                userEntity.privacystatusid!!.chat,
                userEntity.userEvents,
                userEntity.hobbytypeid!!.name,
                userEntity.friendsid,
                userEntity.userColorid!!.name,
                userEntity.userLanguageid!!.name)
        return privateUserDTO
    }

    fun userToUserDTO(userEntity:UserEntity): UserDTO{
        val userDTO = UserDTO(
                userEntity.id,
                userEntity.login,
                null,
                userEntity.age,
                userEntity.avatarimage,
                userEntity.images,
                null,
                userEntity.ratingid!!.rating,
                userEntity.ratingid!!.ratingByUser.size,
                userEntity.privacystatusid!!.profile,
                userEntity.privacystatusid!!.map,
                userEntity.privacystatusid!!.chat,
                userEntity.userEvents,
                userEntity.hobbytypeid!!.name,
                Friends(userEntity.friendsid!!.id, userEntity.friendsid!!.friendlist, arrayOf(), arrayOf()),
                null,
                null)
        return userDTO
    }

    fun userToClosedUserDTO(userEntity:UserEntity): UserDTO{
        val userDTO = UserDTO(
                userEntity.id,
                userEntity.login,
                null,
                userEntity.age,
                userEntity.avatarimage,
                null,
                null,
                userEntity.ratingid!!.rating,
                userEntity.ratingid!!.ratingByUser.size,
                userEntity.privacystatusid!!.profile,
                userEntity.privacystatusid!!.map,
                userEntity.privacystatusid!!.chat,
                null,
                null,
                null,
                null,
                null)
        return userDTO
    }

    fun userToBlacklistDTO(userEntity:UserEntity): UserDTO{
        val blacklistUserDTO = UserDTO(
                userEntity.id,
                userEntity.login,
                null,
                userEntity.age,
                userEntity.avatarimage,
                null,
                null,
                userEntity.ratingid!!.rating,
                userEntity.ratingid!!.ratingByUser.size,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
        return blacklistUserDTO
    }
}