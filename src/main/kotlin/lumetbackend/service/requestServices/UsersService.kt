package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.DTO.PrivateUserDTO
import lumetbackend.entities.DTO.UserBlacklistDTO
import lumetbackend.entities.DTO.UserDTO
import lumetbackend.entities.EventEntity
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
        return ResponseEntity(userToprivateUserDTO(userEntity), HttpStatus.OK)
    }
//
//    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
//        val userEntity = getUserByRequest(request)
//        val userEntityList = userService.findAll()
//        if(userEntity!=null) {
//            val userDTO = mutableListOf<UserDTO>()
//            for (user in userEntityList) {
//                if (user.id!=userEntity.id){
//                    if (user.privacystatus == "ALL"){
//                        if (!user.blacklist.contains(userEntity.id)) userDTO.add(UserDTO(user.id, user.login, user.status, user.age, user.avatarimage, user.rating, user.hobbytype, user.events, user.friendlist, userEntity.images))
//                    }
//                    else {
//                        if (user.privacystatus == "FRIENDSONLY" && user.friendlist.contains(userEntity.id)){
//                            userDTO.add(UserDTO(user.id, user.login, user.status, user.age, user.avatarimage, user.rating, user.hobbytype, user.events, user.friendlist, userEntity.images))
//                        }
//                    }
//                }
//            }
//            return ResponseEntity(userDTO, HttpStatus.OK)
//        }else return ResponseEntity(HttpStatus.NOT_FOUND)
//    }
//
    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        return if(userEntity!=null) {
            var userFriends = userEntity.friendsid!!.friendlist

            val userDTO = mutableListOf<UserDTO>()
            for (id in userFriends){
                try {
                    val user = userService.findById(id).get()
                    userDTO.add(userToUserDTO(user))

                } catch (_: NoSuchElementException) {
                    userFriends = arrayService.removeInt(userFriends, id)
                }
            }
            userEntity.friendsid!!.friendlist = userFriends
            userService.save(userEntity)
            ResponseEntity(userDTO, HttpStatus.OK)
        }else ResponseEntity(HttpStatus.NOT_FOUND)
    }
//
//    fun getBlacklist(request: HttpServletRequest): ResponseEntity<Any>{
//        val userEntity = getUserByRequest(request)
//        return if(userEntity!=null) {
//            var userBlacklist = userEntity.blacklist
//            val userDTO = mutableListOf<UserBlacklistDTO>()
//            for (id in userEntity.blacklist){
//                try {
//                    val user = userService.findById(id).get()
//                    userDTO.add(UserBlacklistDTO(user.id, user.login, user.age, user.avatarimage, user.rating))
//                } catch (_: NoSuchElementException) {
//                    userBlacklist = arrayService.removeInt(userBlacklist, id)
//                }
//            }
//            userEntity.blacklist = userBlacklist
//            userService.save(userEntity)
//            ResponseEntity(userDTO, HttpStatus.OK)
//        }else ResponseEntity(HttpStatus.NOT_FOUND)
//    }
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

    fun userToprivateUserDTO(userEntity:UserEntity): PrivateUserDTO{
        val privateUserDTO = PrivateUserDTO(
                userEntity.id,
                userEntity.login,
                userEntity.email,
                userEntity.age,
                userEntity.avatarimage,
                userEntity.images,
                userEntity.blacklist,
                userEntity.ratingid!!.rating,
                userEntity.privacystatusid!!.name,
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
                userEntity.age,
                userEntity.avatarimage,
                userEntity.images,
                userEntity.ratingid!!.rating,
                userEntity.hobbytypeid!!.name)
        return userDTO
    }
}