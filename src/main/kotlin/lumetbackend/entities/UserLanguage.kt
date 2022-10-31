package lumetbackend.entities

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "user_language")
open class UserLanguage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Size(max = 255)
    @Column(name = "name")
    open var name: String? = null

}