package lumetbackend.controller

import lumetbackend.config.jwt.JwtFilter
import lumetbackend.config.jwt.JwtProvider
import lumetbackend.entities.DTO.PrivateUserDTO
import lumetbackend.entities.UserEntity
import lumetbackend.service.arrayService.ArrayService
import lumetbackend.service.databaseService.UserService
import lumetbackend.service.requestServices.UserChangesService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/users/change_user")
class UserChangesController(private val userChangesService: UserChangesService) {

    @PostMapping("/change_login")
    fun changeLogin(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any> {
        return userChangesService.changeLogin(request, stringRequest)
    }

//    @PostMapping("/change_password")
//    fun changePassword(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }
//
//    @PostMapping("/change_email")
//    fun changeEmail(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }

    @PostMapping("/change_privacystatus")
    fun changePrivacyStatus(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.changePrivacyStatus(request, stringRequest)
    }

    @PostMapping("/change_age")
    fun changeAge(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.changeAge(request, stringRequest)
    }

//    @PostMapping("/change_avatarimage")
//    fun changeAvatarImage(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.NOT_FOUND)
//    }

    @PostMapping("/change_hobbytype")
    fun changeHobbyType(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.changeHobbyType(request, stringRequest)
    }


    @PostMapping("/add_to_blacklist")
    fun AddToBlacklist(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.AddToBlacklist(request, stringRequest)
    }
    @PostMapping("/delete_frome_blacklist")
    fun DeleteFromeBlacklist(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.DeleteFromeBlacklist(request, stringRequest)
    }


    @PostMapping("/add_to_friendlist")
    fun AddToFriendlist(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.AddToFriendlist(request, stringRequest)
    }
    @PostMapping("/delete_frome_friendlist")
    fun DeleteFromeFriendlist(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.DeleteFromeFriendlist(request, stringRequest)
    }


//    @PostMapping("/add_to_images")
//    fun AddToImages(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }
//    @PostMapping("/delete_frome_images")
//    fun DeleteFromeImages(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }


//    @PostMapping("/add_to_events_participation")
//    fun AddToEventsParticipation(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }
//    @PostMapping("/delete_frome_events_participation")
//    fun DeleteFromeEventsParticipation(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
//        return ResponseEntity(HttpStatus.FORBIDDEN)
//    }

}
