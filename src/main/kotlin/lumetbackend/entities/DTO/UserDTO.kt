package lumetbackend.entities.DTO

import lumetbackend.entities.Friends
import lumetbackend.entities.UserEvent


class UserDTO(
        var id: Int?,
        var login: String?,
//        var status: String?,
//        var age: Int?,
//        var avatarimage: String?,
//        var rating: Int?,
//        var hobbytype: String?,
//        var events: Array<Int>?,
//        var friendlist: Array<Int>?,
//        var images: Array<String>?
)


class PrivateUserDTO(
        var id: Int?,
        var login: String?,
        var email: String?,
        var age: Int?,
        var avatarimage: String?,
        var images: Array<String>?,
        var blacklist: Array<Int>?,
        var rating: Int?,
        var privacystatus: String?,
        var userEvents: UserEvent?,
        var hobbytype: String?,
        var friends: Friends?,
        var userColor: String?,
        var userLanguage: String?
)

class UserBlacklistDTO(
        var id: Int?,
        var login: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,

)