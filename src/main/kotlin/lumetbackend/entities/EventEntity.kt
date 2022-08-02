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
@Table(name = "event", schema = "public", catalog = "lumetdatabase")
open class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "userid")
    open var userid: Int? = null

    @Column(name = "userrating")
    open var userrating: Int? = null

    @Column(name = "name")
    open var name: String? = null

    @Column(name = "description", length = 500)
    open var description: String? = null

    @Column(name = "hobbytype", length = 50)
    open var hobbytype: String? = null

    @Column(name = "avatarimage")
    open var avatarimage: String? = null

    @Column(name = "privacystatus", nullable = false, length = 50)
    open var privacystatus: String? = null

    @Column(name = "\"time\"")
    open var time: String? = null

    @Column(name = "coordinates")
    open var coordinates: String? = null

    @Column(name = "desiredage")
    open var desiredage: Int? = null

    @Column(name = "participantsanonymity")
    open var participantsanonymity: Int? = null

    @Type(type = "int-array")
    @Column(name = "participants")
    open var participants: Array<Int> = arrayOf()

}


