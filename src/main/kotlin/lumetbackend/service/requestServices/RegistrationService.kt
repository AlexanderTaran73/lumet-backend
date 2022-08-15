package lumetbackend.service.requestServices

import lumetbackend.config.jwt.JwtProvider
import lumetbackend.controller.EmailConfirmationRequest
import lumetbackend.controller.RegistrationRequest
import lumetbackend.entities.RegistrationDataEntity
import lumetbackend.entities.UserEntity
import lumetbackend.service.databaseService.RegistrationDataService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.emailService.EmailService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import javax.validation.Valid

@Service
class RegistrationService(private val registrationDataService: RegistrationDataService, private val userService: UserService, private val emailService: EmailService, private val jwtProvider: JwtProvider){

    fun Registration(registrationRequest: RegistrationRequest): ResponseEntity<Any> {
        val login = registrationRequest.login
        val password = registrationRequest.password
        val email = registrationRequest.email

        registrationDataService.deleteByEmail(email)
        if (login.length<2) return ResponseEntity(HttpStatus.valueOf("Incorrect login"))
        if (userService.findByEmail(email)!=null) return ResponseEntity(HttpStatus.valueOf("Email already registered"))

        val emailtoken = (1000..9999).random()

        val registrationDataEntity = RegistrationDataEntity(login, password, email, emailtoken)
        registrationDataService.save(registrationDataEntity)

        emailService.sendEmailToken(email, emailtoken.toString())

        return ResponseEntity(HttpStatus.CREATED)
    }

    fun EmailConfirmation(emailConfirmationRequest: EmailConfirmationRequest): ResponseEntity<Any> {

        val email = emailConfirmationRequest.email
        val emailtoken = emailConfirmationRequest.emailtoken

        val registrationDataEntity = registrationDataService.findByEmail(email)
        if (registrationDataEntity==null) return ResponseEntity(HttpStatus.NOT_FOUND)
        if (registrationDataEntity.emailtoken!=emailtoken) return ResponseEntity((HttpStatus.valueOf("Invalid token")))
        val userEntity = UserEntity(registrationDataEntity.login, registrationDataEntity.password, registrationDataEntity.email)
        userService.firstsave(userEntity)
        registrationDataService.deleteByEmail(email)
        return ResponseEntity(jwtProvider.generateToken(userEntity.email), HttpStatus.CREATED)
    }
}