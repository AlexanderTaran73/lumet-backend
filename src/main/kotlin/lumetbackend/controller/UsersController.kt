package lumetbackend.controller

import lumetbackend.service.imageservice.service.FileService
import lumetbackend.service.requestServices.UsersService
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping("/getUser")
    fun getUser(request: HttpServletRequest): ResponseEntity<Any> {
        return usersService.getUser(request)
    }
//
//    @GetMapping("/getALLUsers")
//    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
//        return usersService.getALLUsers(request)
//    }        TODO сделать что-то
//
    @GetMapping("/getFriends")
    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getFriends(request)
    }
//
//    @GetMapping("/getBlacklist")
//    fun getBlacklist(request: HttpServletRequest): ResponseEntity<Any>{
//        return usersService.getBlacklist(request)
//    }
//
//    @GetMapping("/getUserEvents")
//    fun getUserEvents(request: HttpServletRequest): ResponseEntity<Any>{
//        return usersService.getUserEvents(request)
//    }
}

