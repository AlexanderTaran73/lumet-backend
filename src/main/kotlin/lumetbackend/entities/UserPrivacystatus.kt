package lumetbackend.entities

import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "user_privacystatus")
open class UserPrivacystatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "profile", nullable = false)
    open var profile: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "map", nullable = false)
    open var map: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "chat", nullable = false)
    open var chat: String? = null

    constructor(profile: String?, map: String?, chat: String?) {
        this.profile = profile
        this.map = map
        this.chat = chat
    }
}