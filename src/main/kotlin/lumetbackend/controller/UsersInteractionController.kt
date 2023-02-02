package lumetbackend.controller

import lumetbackend.service.requestServices.UsersInteractionService
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/users/users_interaction")
class UsersInteractionController(private val usersInteractionService: UsersInteractionService) {


//    Blacklist

    @PostMapping("/add_to_blacklist/{userId}")
    fun addToBlacklist(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.addToBlacklist(request, userId)
    }
    @PostMapping("/delete_frome_blacklist/{userId}")
    fun deleteFromeBlacklist(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.deleteFromeBlacklist(request, userId)
    }


//    Friends

    @PostMapping("/friend_request/{userId}")
    fun friendRequest(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.friendRequest(request, userId)
    }

    @PostMapping("/cancel_friend_request/{userId}")
    fun cancelFriendRequest(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.cancelFriendRequest(request, userId)
    }

    @PostMapping("/accept_request/{userId}")
    fun acceptRequest(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.acceptRequest(request, userId)
    }

    @PostMapping("/reject_request/{userId}")
    fun rejectRequest(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.rejectRequest(request, userId)
    }

    @PostMapping("/remove_from_friends/{userId}")
    fun removeFromFriends(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.removeFromFriends(request, userId)
    }


//    Rating

    @PostMapping("/give_feedback/{userId}/{feedback}")
    fun giveFeedback(request: HttpServletRequest, @PathVariable @NotNull userId: Int,@PathVariable @NotNull feedback:Int): ResponseEntity<Any> {
        return usersInteractionService.giveFeedback(request, userId, feedback)
    }

    @PostMapping("/cancel_feedback/{userId}")
    fun cancelFeedback(request: HttpServletRequest, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.cancelFeedback(request, userId)
    }

//    Events

    @PostMapping("/apply_for_event/{eventId}")
    fun applyForEvent(request: HttpServletRequest, @PathVariable @NotNull eventId: Int): ResponseEntity<Any> {
        return usersInteractionService.applyForEvent(request, eventId)
    }

    @PostMapping("/remove_apply_for_event/{eventId}")
    fun removeApplyForEvent(request: HttpServletRequest, @PathVariable @NotNull eventId: Int): ResponseEntity<Any> {
        return usersInteractionService.removeApplyForEvent(request, eventId)
    }

    @PostMapping("/confirm_apply_for_event/{eventId}/{userId}")
    fun confirmApplyForEvent(request: HttpServletRequest, @PathVariable @NotNull eventId: Int, @PathVariable @NotNull userId: Int): ResponseEntity<Any> {
        return usersInteractionService.confirmApplyForEvent(request, eventId, userId)
    }



}