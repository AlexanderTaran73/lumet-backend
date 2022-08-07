package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.service.imageservice.service.FileService
import lumetbackend.entities.DTO.PrivateUserDTO
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest

@Service
class UserChangesService(private val jwtProvider: JwtProvider, private val userService: UserService, private val arrayService: ArrayService, private val passwordEncoder : PasswordEncoder, private val fileService: FileService) {

    fun changeLogin(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (stringRequest.length<2) return ResponseEntity(HttpStatus.valueOf("Incorrect login"))
        if (userEntity != null) {
            userEntity.login = stringRequest
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    fun changePassword(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if(stringRequest.length<4) return ResponseEntity(HttpStatus.NOT_FOUND)
        if (userEntity != null) {
            userEntity.password = stringRequest
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }
//
//    @PostMapping("/change_email")
//    fun changeEmail(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }


    fun changePrivacyStatus(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if(stringRequest in arrayOf("ALL", "NOBODY", "FRIENDSONLY")){
            if (userEntity != null) {
                userEntity.privacystatus = stringRequest
                userService.save(userEntity)
                return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
            }
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    fun changeAge(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (userEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val age = stringRequest.toInt()
            if (age>0 && age<200){
                userEntity.age = age
                userService.save(userEntity)
                return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
            }
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    fun changeAvatarImage(imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if (userEntity!=null){
            val fileName = fileService.saveImage(imageFile)
            userEntity.avatarimage = fileName
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }


    fun changeHobbyType(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if(stringRequest in arrayOf("RUN", "BIKE", "SPORT FIELD", null)){
            if (userEntity != null) {
                userEntity.hobbytype = stringRequest
                userService.save(userEntity)
                return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
            }
        }
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }



    fun AddToBlacklist(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (userEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val id = stringRequest.toInt()
            val blackUser = userService.findById(id).get()
            userEntity.blacklist = arrayService.appendInt(userEntity.blacklist, id)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    fun DeleteFromeBlacklist(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (userEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val id = stringRequest.toInt()
            userEntity.blacklist = arrayService.removeInt(userEntity.blacklist, id)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }



    fun AddToFriendlist(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (userEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val id = stringRequest.toInt()
            val friendUser = userService.findById(id).get()
            userEntity.friendlist = arrayService.appendInt(userEntity.friendlist, id)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    fun DeleteFromeFriendlist(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any> {
        val userEntity = getUserByRequest(request)
        if (userEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        try {
            val id = stringRequest.toInt()
            userEntity.friendlist = arrayService.removeInt(userEntity.friendlist, id)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }catch (_:Exception){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }



    fun AddToImages(imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if (userEntity!=null){
            val fileName = fileService.saveImage(imageFile)
            userEntity.images = arrayService.appendString(userEntity.images, fileName)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    fun DeleteFromeImages(request: HttpServletRequest, stringRequest: String): ResponseEntity<Any>{
        val userEntity = getUserByRequest(request)
        if (userEntity!=null){
            userEntity.images = arrayService.removeString(userEntity.images, stringRequest)
            userService.save(userEntity)
            return ResponseEntity(UserToUserDTO(userEntity), HttpStatus.OK)
        }else return ResponseEntity(HttpStatus.NOT_FOUND)
    }


//    @PostMapping("/add_to_events_participation")
//    fun AddToEventsParticipation(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }
//    @PostMapping("/delete_frome_events_participation")
//    fun DeleteFromeEventsParticipation(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }









    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }

    fun UserToUserDTO(userEntity: UserEntity): PrivateUserDTO {
        return PrivateUserDTO(userEntity.id, userEntity.login, userEntity.email , userEntity.status, userEntity.privacystatus, userEntity.age, userEntity.avatarimage, userEntity.rating, userEntity.hobbytype, userEntity.events, userEntity.friendlist, userEntity.blacklist, userEntity.images)
    }
}