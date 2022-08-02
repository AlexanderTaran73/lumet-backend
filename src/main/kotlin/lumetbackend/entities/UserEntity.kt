package lumetbackend.entities

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.*

@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
        TypeDef(name = "string-array", typeClass = StringArrayType::class)
)

@Entity
@Table(name = "user", schema = "public", catalog = "lumetdatabase")
open class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null


    @Column(name = "login", nullable = false)
    open var login: String? = null

    @Column(name = "password", nullable = false)
    open var password: String? = null

    @Column(name = "email", nullable = false)
    open var email: String? = null

    @Column(name = "emailtoken")
    open var emailtoken: Int? = null

    @Column(name = "status", nullable = false, length = 10)
    open var status: String = "OFFLINE"

    @Column(name = "privacystatus", nullable = false, length = 50)
    open var privacystatus: String = "ALL"

    @Column(name = "age")
    open var age: Int? = null

    @Column(name = "avatarimage")
    open var avatarimage: String? = null

    @Column(name = "rating", nullable = false)
    open var rating: Int = 10

    @Column(name = "hobbytype", length = 50)
    open var hobbytype: String? = null

    @ManyToOne
    @JoinColumn(name = "roleid", nullable = false)
    open var roleid: RoleEntity? = null

    @Type(type = "int-array")
    @Column(name = "blacklist")
    open var blacklist: Array<Int> = arrayOf()

    @Type(type = "int-array")
    @Column(name = "friendlist")
    open var friendlist: Array<Int> = arrayOf()

    @Type(type = "int-array")
    @Column(name = "events")
    open var events: Array<Int> = arrayOf()

    @Type(type = "string-array")
    @Column(name = "images")
    open var images: Array<String> = arrayOf()


    constructor(login: String?, password: String?, email: String?) {
        this.login = login
        this.password = password
        this.email = email
    }
}