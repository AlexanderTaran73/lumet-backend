package lumetbackend.controller

import lumetbackend.service.imageservice.service.FileService
import lumetbackend.service.requestServices.UsersService
import org.jetbrains.annotations.NotNull
import org.springframework.core.io.FileSystemResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UsersController(private val usersService: UsersService) {

    @GetMapping("/getUser")
    fun getUser(request: HttpServletRequest): ResponseEntity<Any> {
        return usersService.getUser(request)
    }

    @GetMapping("/getUserById/{userId}")
    fun getUserById(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any>{
        return usersService.getUserById(request, userId)
    }

    @GetMapping("/getUserListById")
    fun getUserListById(request: HttpServletRequest, @Valid @RequestBody userIdList: List<Int>): ResponseEntity<Any> {
        return usersService.getUserListById(request, userIdList)
    }

    @GetMapping("/getALLUsers")
    fun getALLUsers(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getALLUsers(request)
    }

    @GetMapping("/getALLUsers/sort_and_search/{search}/{min_age}/{rating}/{hobby}")
    fun getALLUsersSort(request: HttpServletRequest, @PathVariable @NotNull search:String, @PathVariable @NotNull min_age:Int, @PathVariable @NotNull rating:Int, @PathVariable @NotNull hobby:String): ResponseEntity<Any>{
        return usersService.getALLUsersSort(request, search, min_age, rating, hobby)
    }


//    НЕ имеет смысла? аналогично getUserListById

//    @GetMapping("/getFriends")
//    fun getFriends(request: HttpServletRequest): ResponseEntity<Any>{
//        return usersService.getFriends(request)
//    }

    @GetMapping("/getBlacklist")
    fun getBlacklist(request: HttpServletRequest): ResponseEntity<Any>{
        return usersService.getBlacklist(request)
    }


//
//    @GetMapping("/getUserEvents")
//    fun getUserEvents(request: HttpServletRequest): ResponseEntity<Any>{
//        return usersService.getUserEvents(request)
//    }
}

