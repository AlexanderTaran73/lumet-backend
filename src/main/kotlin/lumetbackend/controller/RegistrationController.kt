package lumetbackend.controller

import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.RegistrationDataService
import lumetbackend.service.UserService
import lumetbackend.service.emailservice.EmailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/registration")
class RegistrationController(private val registrationDataService: RegistrationDataService, private val userService: UserService, private val emailService: EmailService, private val jwtProvider: JwtProvider) {


    @PostMapping
    fun Registration(@Valid @RequestBody registrationRequest: RegistrationRequest): ResponseEntity<Any> {
        val login = registrationRequest.login
        val password = registrationRequest.password
        val email = registrationRequest.email

        registrationDataService.deleteByEmail(email)
        if (userService.findByEmail(email)!=null) return ResponseEntity(HttpStatus.valueOf("Email already registered"))

        val emailtoken = (1000..9999).random()

        val registrationDataEntity = RegistrationDataEntity(login, password, email, emailtoken)
        registrationDataService.save(registrationDataEntity)

        emailService.sendEmailToken(email, emailtoken.toString())

        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/email_confirmation")
    fun EmailConfirmation(@Valid @RequestBody emailConfirmationRequest: EmailConfirmationRequest): ResponseEntity<Any> {

        val email = emailConfirmationRequest.email
        val emailtoken = emailConfirmationRequest.emailtoken

        val registrationDataEntity = registrationDataService.findByEmail(email)
        if (registrationDataEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        if (registrationDataEntity.emailtoken!=emailtoken) return ResponseEntity((HttpStatus.valueOf("Invalid token")))
        val userEntity = UserEntity(registrationDataEntity.login, registrationDataEntity.password, registrationDataEntity.email)
        userService.save(userEntity)
        registrationDataService.deleteByEmail(email)
        return ResponseEntity(jwtProvider.generateToken(userEntity.email), HttpStatus.CREATED)
    }
}

data class RegistrationRequest(
        val login : String,
        val password : String,
        val email:String
        )

data class EmailConfirmationRequest(
        val email:String,
        val emailtoken:Int
)

