package lumetbackend.controller

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/map")
class UsersOnMapController (
        private val simpleMessageTemplate: SimpMessagingTemplate
){

    @MessageMapping("/user")
//    @SendTo("/topic/map")
    fun mapUser(userMapDTO: UserMapDTO){
        sendMessageToUsers(userMapDTO)
    }

    private fun sendMessageToUsers(userMapDTO: UserMapDTO) {

        simpleMessageTemplate.convertAndSend("/topic/map", userMapDTO)
    }

}
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserMapDTO(
        var id:Int?,
        var name:String?,
        var avatarimage:String?,
        var rating: Int?,
        var hobbytype:String?,
        var latitude:String?,
        var longitude:String?,
        var status:String?,
)

