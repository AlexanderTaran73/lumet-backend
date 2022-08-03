package lumetbackend.controller

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.DTO.PrivateUserDTO
import lumetbackend.entities.DTO.UserBlacklistDTO
import lumetbackend.entities.DTO.UserDTO
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.requestServices.UsersService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.NoSuchElementException

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping("/getUser")
    fun getUser(request: HttpServletRequest): ResponseEntity<Any> {
        return usersService.getUser(request)
    }

    @GetMapping("/getALLUsers")
    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getALLUsers(request)
    }

    @GetMapping("/getFriends")
    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getFriends(request)
    }

    @GetMapping("/getBlacklist")
    fun getBlacklist(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getBlacklist(request)
    }

}

