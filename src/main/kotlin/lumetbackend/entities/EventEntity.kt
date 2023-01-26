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
open class     EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "userrating")
    open var userrating: Int? = null

    @Column(name = "userid", nullable = false)
    open var userid: Int? = null

    @Column(name = "name")
    open var name: String? = null

    @Column(name = "description", length = 500)
    open var description: String? = null

    @Column(name = "hobbytype", length = 50)
    open var hobbytype: String? = null

    @Column(name = "avatarimage")
    open var avatarimage: String? = null

    @Column(name = "\"time\"")
    open var time: String? = null

    @Column(name = "desiredage")
    open var desiredage: Int? = null

    @Column(name = "participant_limit")
    open var participantLimit: Int? = null

    @Column(name = "participants_anonymity", nullable = false)
    open var participantsAnonymity: String? = null

    @Column(name = "privacy_status", nullable = false, length = 50)
    open var privacyStatus: String? = null

    @Column(name = "registration_settings", nullable = false)
    open var registrationSettings: String? = null

    @Type(type = "int-array")
    @Column(name = "confirmed_participants")
    open var confirmedParticipants: Array<Int> = arrayOf()

    @Type(type = "int-array")
    @Column(name = "unconfirmed_participants")
    open var unconfirmedParticipants: Array<Int> = arrayOf()

    @Column(name = "latitude")
    open var latitude: String? = null

    @Column(name = "longitude")
    open var longitude: String? = null

    constructor(userrating: Int?, userid: Int?, name: String?, description: String?, hobbytype: String?, time: String?, desiredage: Int?, participantLimit: Int?, participantsAnonymity: String?, privacyStatus: String?, registrationSettings: String?, latitude: String?, longitude: String?) {
        this.userrating = userrating
        this.userid = userid
        this.name = name
        this.description = description
        this.hobbytype = hobbytype
        this.time = time
        this.desiredage = desiredage
        this.participantLimit = participantLimit
        this.participantsAnonymity = participantsAnonymity
        this.privacyStatus = privacyStatus
        this.registrationSettings = registrationSettings
        this.latitude = latitude
        this.longitude = longitude
    }
}


