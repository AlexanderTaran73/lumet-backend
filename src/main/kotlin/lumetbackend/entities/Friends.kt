package lumetbackend.entities

import com.vladmihalcea.hibernate.type.array.IntArrayType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.annotations.TypeDefs
import javax.persistence.*
import javax.validation.constraints.NotNull

@TypeDefs(
        TypeDef(name = "int-array", typeClass = IntArrayType::class),
)

@Entity
@Table(name = "friends", schema = "public", catalog = "lumetdatabase")
open class Friends{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null


    @NotNull
    @Type(type = "int-array")
    @Column(name = "friendlist", nullable = false)
    open var friendlist: Array<Int> = arrayOf()

    @NotNull
    @Type(type = "int-array")
    @Column(name = "user_requests", nullable = false)
    open var userRequests: Array<Int> = arrayOf()

    @NotNull
    @Type(type = "int-array")
    @Column(name = "requests_to_user", nullable = false)
    open var requestsToUser: Array<Int> = arrayOf()

    constructor(id: Int?, friendlist: Array<Int>, userRequests: Array<Int>, requestsToUser: Array<Int>) {
        this.id = id
        this.friendlist = friendlist
        this.userRequests = userRequests
        this.requestsToUser = requestsToUser
    }
}