package lumetbackend.entities

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
        TypeDef(name = "string-array", typeClass = StringArrayType::class)
)

@Entity
@Table(name = "user", schema = "public", catalog = "lumetdatabase")
open class UserEntity {
    constructor(login: String?, password: String?, email: String?) {
        this.login = login
        this.password = password
        this.email = email
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "login", nullable = false)
    open var login: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "age")
    open var age: Int? = null

    @Size(max = 255)
    @Column(name = "avatarimage")
    open var avatarimage: String? = null

    @Type(type = "string-array")
    @Column(name = "images")
    open var images: Array<String> = arrayOf()

    @Type(type = "int-array")
    @Column(name = "blacklist")
    open var blacklist: Array<Int> = arrayOf()



    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_statusid", nullable = false)
    open var accountStatusid: AccountStatus? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "roleid", nullable = false)
    open var roleid: RoleEntity? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "ratingid", nullable = false)
    open var ratingid: UserRating? = null

//

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "privacystatusid", nullable = false)
    open var privacystatusid: UserPrivacystatus? = null

//

    @NotNull
    @ManyToOne( optional = false)
    @JoinColumn(name = "user_events", nullable = false)
    open var userEvents: UserEvent? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "hobbytypeid", nullable = false)
    open var hobbytypeid: Hobbytype? = null


    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "friendsid", nullable = false)
    open var friendsid: Friends? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_colorid", nullable = false)
    open var userColorid: UserColor? = null

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_languageid", nullable = false)
    open var userLanguageid: UserLanguage? = null
}