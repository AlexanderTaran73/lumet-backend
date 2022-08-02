package lumetbackend.controller

import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.RegistrationDataService
import lumetbackend.service.UserService
import lumetbackend.service.emailservice.EmailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/authorization")
class AuthorizationController(private val userService: UserService, private val emailService:EmailService, private val registrationDataService: RegistrationDataService, private val jwtProvider: JwtProvider, private val passwordEncoder : PasswordEncoder) {


    @GetMapping
    fun Authorization(@Valid @RequestBody authorizationRequest: AuthorizationRequest): ResponseEntity<Any> {
        val userEntity = userService.findByEmailAndPassword(authorizationRequest.email, authorizationRequest.password)
        if(userEntity!=null) return ResponseEntity(jwtProvider.generateToken(userEntity!!.email), HttpStatus.OK)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PostMapping("/recovery")
    fun Recovery(@Valid @RequestBody authorizationRequest: AuthorizationRequest): ResponseEntity<Any>{
        val login = "Recovery"
        val password = authorizationRequest.password
        val email = authorizationRequest.email
        registrationDataService.deleteByEmail(email)
        if (userService.findByEmail(email)==null) return ResponseEntity(HttpStatus.valueOf("Email not registered"))
        val emailtoken = (1000..9999).random()
        val registrationDataEntity = RegistrationDataEntity(login, password, email, emailtoken)
        registrationDataService.save(registrationDataEntity)
        emailService.sendEmailToken(email, emailtoken.toString())
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/recovery/confirmation")
    fun RecoveryConfirmation(@Valid @RequestBody confirmationRequest: ConfirmationRequest): ResponseEntity<Any>{

        val email = confirmationRequest.email
        val emailtoken = confirmationRequest.emailtoken

        val registrationDataEntity = registrationDataService.findByEmail(email)
        if (registrationDataEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        if (registrationDataEntity.emailtoken!=emailtoken) return ResponseEntity((HttpStatus.valueOf("Invalid token")))

        val userEntity = userService.findByEmail(email)

        userEntity!!.password=passwordEncoder.encode(registrationDataEntity.password)
        userService.save(userEntity)

        registrationDataService.deleteByEmail(email)

        return ResponseEntity(userEntity, HttpStatus.CREATED)
    }
}

data class AuthorizationRequest(
        val email:String,
        val password : String
)

data class ConfirmationRequest(
        val email:String,
        val emailtoken:Int
)