package lumetbackend.controller

import lumetbackend.entities.UserEntity
import lumetbackend.service.databaseService.RegistrationDataService
import lumetbackend.service.databaseService.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/test")
class TestController(private val registrationDataService: RegistrationDataService, private val userService: UserService) {


    @GetMapping
    fun test():ResponseEntity<Any>{

        val userEntity = UserEntity("test", "test", "1")
        userService.save(userEntity)



        return ResponseEntity(HttpStatus.OK)
    }

}
