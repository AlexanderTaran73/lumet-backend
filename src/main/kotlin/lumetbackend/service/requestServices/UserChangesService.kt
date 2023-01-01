package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.service.imageservice.service.FileService
import lumetbackend.entities.DTO.PrivateUserDTO
import lumetbackend.entities.UserEntity
import lumetbackend.repositories.HobbytypeRepository
import lumetbackend.repositories.UserColorRepository
import lumetbackend.repositories.UserLanguageRepository
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@Service
class UserChangesService(private val jwtProvider: JwtProvider,
                         private val userService: UserService,
                         private val arrayService: ArrayService,
                         private val passwordEncoder : PasswordEncoder,
                         private val fileService: FileService,
                         private val hobbytypeRepository: HobbytypeRepository,
                         private val userLanguageRepository: UserLanguageRepository,
                         private val userColorRepository: UserColorRepository,) {





    fun changeLogin(request: HttpServletRequest, login: String): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        if (login.length<2) return ResponseEntity(HttpStatus.valueOf("Incorrect login"))
        user.login = login
        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun changePassword(request: HttpServletRequest, password: String): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!
        user.password = password
        userService.firstsave(user)
        return ResponseEntity(HttpStatus.OK)
    }


    fun changeAge(request: HttpServletRequest, age: String): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!
        try {
            user.age = age.toInt()
        }
        catch (_: Exception){
            return ResponseEntity(HttpStatus.valueOf("NOT INT"))
        }
        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

//////////

////////

    fun addToBlacklist(request: HttpServletRequest, userId: String): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!
        val blacklist = user.blacklist
        try {
            user.blacklist = arrayService.appendInt(blacklist, userId.toInt())
        }catch (_: Exception){
            return ResponseEntity(HttpStatus.valueOf("NOT INT"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun deleteFromeBlacklist(request: HttpServletRequest, userId: String): ResponseEntity<Any>{
        val user = getUserByRequest(request)!!
        val blacklist = user.blacklist
        try {
            user.blacklist = arrayService.removeInt(blacklist, userId.toInt())
        }catch (_: Exception){
            return ResponseEntity(HttpStatus.valueOf("NOT INT"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }


    fun changeHobbyType(request: HttpServletRequest, hobbytypeName: String): ResponseEntity<Any>{
//    hobbytypeID:
//            0 NOTHING
//            1
//            2
        val user = getUserByRequest(request)!!
        try {
            user.hobbytypeid = hobbytypeRepository.findByName(hobbytypeName)
        }catch (_: Exception) {
            return ResponseEntity(HttpStatus.valueOf("Incorrect hobbytypeName"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun changeColor(request: HttpServletRequest, colorName: String): ResponseEntity<Any> {
//      colorName:
//            0 LIGHT
//            1 DARK
        val user = getUserByRequest(request)!!
        try {
            user.userColorid = userColorRepository.findByName(colorName)
        }catch (_: Exception) {
            return ResponseEntity(HttpStatus.valueOf("Incorrect colorName"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun changeLanguage(request: HttpServletRequest, languageName: String): ResponseEntity<Any> {
//     languageName:
//            0 RUS
//            1 EN
        val user = getUserByRequest(request)!!
        try {
            user.userLanguageid = userLanguageRepository.findByName(languageName)
        }catch (_: Exception) {
            return ResponseEntity(HttpStatus.valueOf("Incorrect languageName"))
        }

        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }




    fun getUserByRequest(request: HttpServletRequest): UserEntity?{
        val bearer = request.getHeader(JwtFilter.AUTHORIZATION)
        val token = if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) { bearer.substring(7) } else null
        return userService.findByEmail(jwtProvider.getEmailFromToken(token))
    }




}