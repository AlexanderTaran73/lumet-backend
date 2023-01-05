package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.service.imageservice.service.FileService
import lumetbackend.entities.UserEntity
import lumetbackend.repositories.HobbytypeRepository
import lumetbackend.repositories.UserColorRepository
import lumetbackend.repositories.UserLanguageRepository
import lumetbackend.repositories.UserPrivacystatusRepository
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.RegistrationDataService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.emailService.EmailService
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest


@Service
class UserChangesService(private val jwtProvider: JwtProvider,
                         private val userService: UserService,
                         private val arrayService: ArrayService,
                         private val fileService: FileService,
                         private val hobbytypeRepository: HobbytypeRepository,
                         private val userLanguageRepository: UserLanguageRepository,
                         private val userColorRepository: UserColorRepository,
                         private val userPrivacystatusRepository: UserPrivacystatusRepository,
                         private val emailService: EmailService,
                         private val registrationDataService: RegistrationDataService,) {





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
        userService.savePassword(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun changeEmail(request: HttpServletRequest, email: String): ResponseEntity<Any> {
        val emailtoken = (1000..9999).random()
        registrationDataService.deleteByEmail(email)
        val registrationDataEntity = RegistrationDataEntity("changeEmail", "changeEmail", email, emailtoken)
        registrationDataService.save(registrationDataEntity)

        emailService.sendEmailToken(email, emailtoken.toString())

        return ResponseEntity(HttpStatus.OK)
    }

    fun changeEmailConfirmation(request: HttpServletRequest, email: String, token: Int): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val registrationDataEntity = registrationDataService.findByEmail(email)

        if (registrationDataEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        if (registrationDataEntity.emailtoken!=token) return ResponseEntity((HttpStatus.valueOf("Invalid token")))

        user.email = email
        registrationDataService.deleteByEmail(email)
        userService.save(user)

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


    fun changeAvatarImage(imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val name = fileService.saveImage(imageFile)
        user.avatarimage = name
        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }

    fun addToImages(imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val name = fileService.saveImage(imageFile)

        user.images = arrayService.appendString(user.images, name)
        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }


    fun deleteFromeImages(request: HttpServletRequest, name: String): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!

        user.images = arrayService.removeString(user.images, name)
        userService.save(user)
        return ResponseEntity(HttpStatus.OK)
    }



    fun changeProfilePrivacyStatus(request: HttpServletRequest, status: String): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val userPrivacystatus = user.privacystatusid!!
        val statusArray = arrayOf("ALL", "FRIENDS", "NOBODY")
        if (status in statusArray){
            userPrivacystatus.profile = status
            userService.userPrivacystatusSave(userPrivacystatus)
            user.privacystatusid = userPrivacystatusRepository.findById(userPrivacystatus.id!!).get()

            userService.save(user)
            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.valueOf("Incorrect status"))
    }

    fun changeMapPrivacyStatus(request: HttpServletRequest, status: String): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val userPrivacystatus = user.privacystatusid!!
        val statusArray = arrayOf("ALL", "FRIENDS", "NOBODY")
        if (status in statusArray){
            userPrivacystatus.map = status
            userService.userPrivacystatusSave(userPrivacystatus)
            user.privacystatusid = userPrivacystatusRepository.findById(userPrivacystatus.id!!).get()

            userService.save(user)
            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.valueOf("Incorrect status"))
    }

    fun changeChatPrivacyStatus(request: HttpServletRequest, status: String): ResponseEntity<Any> {
        val user = getUserByRequest(request)!!
        val userPrivacystatus = user.privacystatusid!!
        val statusArray = arrayOf("ALL", "FRIENDS", "NOBODY")
        if (status in statusArray){
            userPrivacystatus.chat = status
            userService.userPrivacystatusSave(userPrivacystatus)
            user.privacystatusid = userPrivacystatusRepository.findById(userPrivacystatus.id!!).get()

            userService.save(user)
            return ResponseEntity(HttpStatus.OK)
        }
        else return ResponseEntity(HttpStatus.valueOf("Incorrect status"))
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