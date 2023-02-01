package lumetbackend.controller


import lumetbackend.service.requestServices.EventChangesService
import org.jetbrains.annotations.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/events")
class EventsController(private val eventChangesService: EventChangesService) {


    @PostMapping("/create_event")
    fun createEvent(@Valid @RequestBody createEventRequest: CreateEventRequest, request: HttpServletRequest): ResponseEntity<Any> {
        return eventChangesService.createEvent(createEventRequest, request)
    }

    @PostMapping("/remove_event/{eventId}")
    fun removeEvent(request: HttpServletRequest,  @PathVariable @NotNull eventId: Int): ResponseEntity<Any>{
        return eventChangesService.removeEvent(request, eventId)
    }

    @PostMapping("/change_evemt/{eventId}")
    fun changeEvent(@Valid @RequestBody createEventRequest: CreateEventRequest, request: HttpServletRequest,  @PathVariable @NotNull eventId: Int): ResponseEntity<Any>{
        return eventChangesService.changeEvent(createEventRequest, request, eventId)
    }

    @PostMapping("/get_All_events")
    fun getAllEvents(request: HttpServletRequest): ResponseEntity<Any>{
        return eventChangesService.getAllEvents(request)
    }

    @PostMapping("/get_All_events/sort_and_search/{search}/{min_age}/{rating}/{hobby}/{users_limit}")
    fun getAllEventsSort(request: HttpServletRequest, @PathVariable @NotNull search:String, @PathVariable @NotNull min_age:Int, @PathVariable @NotNull rating:Int, @PathVariable @NotNull hobby:String, @PathVariable @NotNull users_limit:Int): ResponseEntity<Any>{
        return eventChangesService.getAllEventsSort(request, search, min_age, rating, hobby,users_limit)
    }

    @PostMapping("/get_events_list_by_id")
    fun getEventsById(request: HttpServletRequest, eventIdList: List<Int>): ResponseEntity<Any> {
        return eventChangesService.getEventsById(request, eventIdList)
    }

    @PostMapping("/get_event_by_id/{eventId}")
    fun getEventById(request: HttpServletRequest, @PathVariable @NotNull eventId: Int): ResponseEntity<Any> {
        return eventChangesService.getEventById(request, eventId)
    }

    @PostMapping("/get_created_events")
    fun getCreaterEvents(request: HttpServletRequest): ResponseEntity<Any>{
        return eventChangesService.getCreaterEvents(request)
    }

    @PostMapping("/change_image/{eventId}")
    fun changeImage(request: HttpServletRequest, @PathVariable @NotNull eventId: Int, @RequestParam("imageFile") imageFile: MultipartFile): ResponseEntity<Any> {
        return eventChangesService.changeImage(request, eventId, imageFile)
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

class EventDTO(
        val id:Int,
        val userrating: Int?,
        val userid: Int?,
        val name: String?,
        val description: String?,
        val hobbytype: String?,
        val avatarimage: String?,
        val time: String?,
        val desiredage: Int?,
        val participantLimit: Int?,
        val participantsAnonymity: String?,
        val privacyStatus: String?,
        val registrationSettings: String?,
        var confirmedParticipants: Array<Int>?,
        var latitude: String?,
        var longitude: String?
)

