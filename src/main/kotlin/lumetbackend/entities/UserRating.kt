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
@Table(name = "user_rating", schema = "public", catalog = "lumetdatabase")
open class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @NotNull
    @Column(name = "rating", nullable = false)
    open var rating: Int? = null

    @Type(type = "int-array")
    @Column(name = "users_id")
    open var usersId: Array<Int> = arrayOf()

    @Type(type = "int-array")
    @Column(name = "rating_by_user")
    open var ratingByUser: Array<Int> = arrayOf()

    constructor(rating: Int?, usersId: Array<Int>, ratingByUser: Array<Int>) {
        this.rating = rating
        this.usersId = usersId
        this.ratingByUser = ratingByUser
    }
}