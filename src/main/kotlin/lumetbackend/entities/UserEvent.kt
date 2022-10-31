package lumetbackend.entities

import com.vladmihalcea.hibernate.type.array.IntArrayType
import com.vladmihalcea.hibernate.type.array.StringArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.*
import javax.validation.constraints.NotNull


@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
)

@Entity
@Table(name = "user_events", schema = "public", catalog = "lumetdatabase")
open class UserEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @NotNull
    @Type(type = "int-array")
    @Column(name = "created_events", nullable = false)
    open var createdEvents: Array<Int> = arrayOf()

    @NotNull
    @Type(type = "int-array")
    @Column(name = "history_created_events", nullable = false)
    open var historyCreatedEvents: Array<Int> = arrayOf()

    @NotNull
    @Type(type = "int-array")
    @Column(name = "participation_events", nullable = false)
    open var participationEvents: Array<Int> = arrayOf()

    @NotNull
    @Type(type = "int-array")
    @Column(name = "history_participation_events", nullable = false)
    open var historyParticipationEvents: Array<Int> = arrayOf()

    constructor(createdEvents: Array<Int>, historyCreatedEvents: Array<Int>, participationEvents: Array<Int>, historyParticipationEvents: Array<Int>) {
        this.createdEvents = createdEvents
        this.historyCreatedEvents = historyCreatedEvents
        this.participationEvents = participationEvents
        this.historyParticipationEvents = historyParticipationEvents
    }
}