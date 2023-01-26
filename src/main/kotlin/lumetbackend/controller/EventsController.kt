package lumetbackend.controller


import lumetbackend.service.requestServices.EventChangesService
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/events")
class EventsController(private val eventChangesService: EventChangesService) {


    @PostMapping("/create_event")
    fun createEvent(@Valid @RequestBody createEventRequest: CreateEventRequest, request: HttpServletRequest): ResponseEntity<Any> {
        return eventChangesService.createEvent(createEventRequest, request)
    }

    @PostMapping("/remove_event/{event_id}")
    fun removeEvent(request: HttpServletRequest,  @PathVariable @NotNull event_id: Int): ResponseEntity<Any>{
        return eventChangesService.removeEvent(request, event_id)
    }

}

class CreateEventRequest(
        val name : String,
        val description : String,
        val hobbytype : String,
        val time : String,
        val desiredage : Int?,
        val participantLimit : Int?,
        val participantsAnonymity : String,
        val privacyStatus : String,
        val registrationSettings : String,
        val latitude : String,
        val longitude : String
)

