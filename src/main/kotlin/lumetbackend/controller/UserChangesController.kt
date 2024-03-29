package lumetbackend.controller


import lumetbackend.service.requestServices.UserChangesService
import org.jetbrains.annotations.NotNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid



@RestController
@RequestMapping("/users/change_user")
class UserChangesController(private val userChangesService: UserChangesService) {

    @PostMapping("/change_login/{login}")
    fun changeLogin(request: HttpServletRequest, @PathVariable @NotNull login: String): ResponseEntity<Any> {
        return userChangesService.changeLogin(request, login)
    }


    @PostMapping("/change_email/{email}")
    fun changeEmail(request: HttpServletRequest, @PathVariable @NotNull email: String): ResponseEntity<Any>{
        return userChangesService.changeEmail(request, email)
    }

    @PostMapping("/change_email/confirmation/{email}/{token}")
    fun changeEmailConfirmation(request: HttpServletRequest, @PathVariable @NotNull email: String, @PathVariable @NotNull token: Int): ResponseEntity<Any>{
        return userChangesService.changeEmailConfirmation(request, email, token)
    }

    @PostMapping("/change_password/{password}")
    fun changePassword(request: HttpServletRequest, @PathVariable @NotNull password: String): ResponseEntity<Any>{
        return userChangesService.changePassword(request, password)
    }

    @PostMapping("/change_age/{age}")
    fun changeAge(request: HttpServletRequest, @PathVariable @NotNull age: String): ResponseEntity<Any>{
        return userChangesService.changeAge(request, age)
    }


    @PostMapping("/change_avatarimage")
    fun changeAvatarImage(@RequestParam("imageFile") imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any>{
        return userChangesService.changeAvatarImage(imageFile, request)
    }

    @PostMapping("/add_to_images")
    fun addToImages(@RequestParam("imageFile") imageFile: MultipartFile, request: HttpServletRequest): ResponseEntity<Any>{
        if (imageFile==null)return ResponseEntity(HttpStatus.LOCKED)
        return userChangesService.addToImages(imageFile, request)
    }
    @PostMapping("/delete_frome_images")
    fun deleteFromeImages(request: HttpServletRequest, @Valid @RequestBody stringRequest: String): ResponseEntity<Any>{
        return userChangesService.deleteFromeImages(request, stringRequest)
    }



    @PostMapping("/change_privacystatus/profile/{status}")
    fun changeProfilePrivacyStatus(request: HttpServletRequest, @PathVariable @NotNull status: String): ResponseEntity<Any>{
        //        ALL, FRIENDS, NOBODY
        return userChangesService.changeProfilePrivacyStatus(request, status)
    }

    @PostMapping("/change_privacystatus/map/{status}")
    fun changeMapPrivacyStatus(request: HttpServletRequest, @PathVariable @NotNull status: String): ResponseEntity<Any>{
        //        ALL, FRIENDS, NOBODY
        return userChangesService.changeMapPrivacyStatus(request, status)
    }

    @PostMapping("/change_privacystatus/chat/{status}")
    fun changeChatPrivacyStatus(request: HttpServletRequest, @PathVariable @NotNull status: String): ResponseEntity<Any>{
        //        ALL, FRIENDS, NOBODY
        return userChangesService.changeChatPrivacyStatus(request, status)
    }


//
//    user_events
//

    @PostMapping("/change_hobbytype/{hobbytypeName}")
    fun changeHobbyType(request: HttpServletRequest, @PathVariable @NotNull hobbytypeName: String): ResponseEntity<Any>{
//    hobbytypeName:
//            0 NOTHING
//            1
//            2
        return userChangesService.changeHobbyType(request, hobbytypeName)
    }


    @PostMapping("/chane_color/{colorName}")
    fun changeColor(request: HttpServletRequest, @PathVariable @NotNull colorName:String): ResponseEntity<Any>{
//    colorName:
//            0 LIGHT
//            1 DARK
        return userChangesService.changeColor(request, colorName)
    }

    @PostMapping("/change_language/{languageName}")
    fun changeLanguage(request: HttpServletRequest, @PathVariable @NotNull languageName:String): ResponseEntity<Any>{
//    languageName:
//            0 RUS
//            1 EN
        return userChangesService.changeLanguage(request, languageName)
    }
}
