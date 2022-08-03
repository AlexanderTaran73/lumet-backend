package lumetbackend.controller

import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.databaseService.RegistrationDataService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.emailService.EmailService
import lumetbackend.service.requestServices.RegistrationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid


@RestController
@RequestMapping("/registration")
class RegistrationController(private val registrationService: RegistrationService) {


    @PostMapping
    fun Registration(@Valid @RequestBody registrationRequest: RegistrationRequest): ResponseEntity<Any> {
        return registrationService.Registration(registrationRequest)
    }

    @PostMapping("/email_confirmation")
    fun EmailConfirmation(@Valid @RequestBody emailConfirmationRequest: EmailConfirmationRequest): ResponseEntity<Any> {
        return registrationService.EmailConfirmation(emailConfirmationRequest)
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

