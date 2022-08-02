package lumetbackend.controller

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.UserEntity
import lumetbackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/users")
class UsersController(private val jwtProvider: JwtProvider, private val userService: UserService) {


    @GetMapping("/getUser")
    fun getUser(request: HttpServletRequest): ResponseEntity<Any> {
        val userEntity = userService.findByEmail(getUserEmail(request)) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        val privateUserDTO = PrivateUserDTO(userEntity.id, userEntity.login, userEntity.status, userEntity.privacystatus, userEntity.age, userEntity.avatarimage, userEntity.rating, userEntity.hobbytype, userEntity.events, userEntity.friendlist, userEntity.blacklist, userEntity.images)
        return ResponseEntity(privateUserDTO, HttpStatus.OK)
    }


    @GetMapping("/getALLUsers")
    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = userService.findByEmail(getUserEmail(request))
        val userEntityList = userService.findAll()
        if(userEntity!=null) {
            val userDTO = mutableListOf<UserDTO>()
            for (user in userEntityList) {
                if (user.id!=userEntity.id){
                    if (user.privacystatus == "ALL"){
                        if (!user.blacklist.contains(userEntity.id)) userDTO.add(UserDTO(user.id, user.login, user.status, user.age, user.avatarimage, user.rating, user.hobbytype, user.events, user.friendlist, userEntity.images))
                    }
                    else {
                        if (user.privacystatus == "FRIENDSONLY" && user.friendlist.contains(userEntity.id)){
                            userDTO.add(UserDTO(user.id, user.login, user.status, user.age, user.avatarimage, user.rating, user.hobbytype, user.events, user.friendlist, userEntity.images))
                            }
                        }
                    }
                }
            return ResponseEntity(userDTO, HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/getFriends")
    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = userService.findByEmail(getUserEmail(request))
        if(userEntity!=null) {
            val userDTO = mutableListOf<UserDTO>()
            for (id in userEntity.friendlist){
                try {
                    val user = userService.findById(id).get()
                    userDTO.add(UserDTO(user.id, user.login, user.status, user.age, user.avatarimage, user.rating, user.hobbytype, user.events, user.friendlist, userEntity.images))
                }
                catch (_:Exception) {

                }
            }
            return ResponseEntity(userDTO, HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }





    fun getUserEmail(request: HttpServletRequest): String{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return jwtProvider.getEmailFromToken(token)
    }


}

class PrivateUserDTO(
        var id: Int?,
        var login: String?,
        var status: String?,
        var privacystatus: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,
        var hobbytype: String?,
        var events: Array<Int>?,
        var friendlist: Array<Int>?,
        var blacklist: Array<Int>?,
        var images: Array<String>?
)

class UserDTO(
        var id: Int?,
        var login: String?,
        var status: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,
        var hobbytype: String?,
        var events: Array<Int>?,
        var friendlist: Array<Int>?,
        var images: Array<String>?
)