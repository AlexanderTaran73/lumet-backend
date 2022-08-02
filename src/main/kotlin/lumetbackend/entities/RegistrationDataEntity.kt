package lumetbackend.entities

import javax.persistence.*


@Entity
@Table(name = "registrationdata", schema = "public", catalog = "lumetdatabase")
open class RegistrationDataEntity(@Column(name = "login", nullable = false)
                                  open var login: String?, @Column(name = "password", nullable = false)
                                  open var password: String?, @Column(name = "email", nullable = false)
                                  open var email: String?, @Column(name = "emailtoken", nullable = true)
                                  open var emailtoken: Int?) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open var id: Int? = null

}