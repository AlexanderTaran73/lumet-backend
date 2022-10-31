package lumetbackend.controller

import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.service.databaseService.RegistrationDataService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.emailService.EmailService
import lumetbackend.service.requestServices.AuthorizationService
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
class AuthorizationController(private val authorizationService: AuthorizationService) {


    @PostMapping
    fun Authorization(@Valid @RequestBody authorizationRequest: AuthorizationRequest): ResponseEntity<Any> {
        return authorizationService.Authorization(authorizationRequest)
    }

    @PostMapping("/recovery")
    fun Recovery(@Valid @RequestBody authorizationRequest: AuthorizationRequest): ResponseEntity<Any>{
        return authorizationService.Recovery(authorizationRequest)
    }

    @PostMapping("/recovery/confirmation")
    fun RecoveryConfirmation(@Valid @RequestBody confirmationRequest: ConfirmationRequest): ResponseEntity<Any>{
        return authorizationService.RecoveryConfirmation(confirmationRequest)
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