package lumetbackend.entities

import javax.persistence.*


@Entity
@Table(name = "role", schema = "public", catalog = "lumetdatabase")
open class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

    @Column(name = "name", nullable = false)
    open var name: String? = null
}