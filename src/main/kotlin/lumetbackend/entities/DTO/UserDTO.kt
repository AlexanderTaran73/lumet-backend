package lumetbackend.entities.DTO

import lumetbackend.entities.Friends
import lumetbackend.entities.UserEvent


class UserDTO(
        var id: Int?,
        var login: String?,
        var email: String?,
        var age: Int?,
        var avatarimage: String?,
        var images: Array<String>?,
        var blacklist: Array<Int>?,
        var rating: Int?,
        var numberOfVotes: Int?,
        var privacystatusProfile: String?,
        var privacystatusMap: String?,
        var privacystatusChat: String?,
        var userEvents: UserEvent?,
        var hobbytype: String?,
        var friends: Friends?,
        var userColor: String?,
        var userLanguage: String?
)
