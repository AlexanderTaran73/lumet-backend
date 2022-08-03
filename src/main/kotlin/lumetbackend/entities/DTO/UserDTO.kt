package lumetbackend.entities.DTO

class UserDTO(
        var id: Int?,
        var login: String?,
        var status: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,
        var hobbytype: String?,
        var events: Array<Int>?,
        var friendlist: Array<Int>?,
        var images: Array<String>?
)


class PrivateUserDTO(
        var id: Int?,
        var login: String?,
        var email: String?,
        var status: String?,
        var privacystatus: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,
        var hobbytype: String?,
        var events: Array<Int>?,
        var friendlist: Array<Int>?,
        var blacklist: Array<Int>?,
        var images: Array<String>?
)

class UserBlacklistDTO(
        var id: Int?,
        var login: String?,
        var age: Int?,
        var avatarimage: String?,
        var rating: Int?,

)