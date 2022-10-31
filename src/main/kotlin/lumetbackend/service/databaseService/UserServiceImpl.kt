package lumetbackend.service.databaseService

import lumetbackend.entities.*
import lumetbackend.repositories.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@Transactional
class UserServiceImpl(private val userRepository: UserRepository,
                      private val roleRepository: RoleRepository,
                      private val passwordEncoder : PasswordEncoder,
                      private val accountStatusRepository: AccountStatusRepository,
                      private val userLanguageRepository: UserLanguageRepository,
                      private val userColorRepository: UserColorRepository,
                      private val friendsRepository: FriendsRepository,
                      private val hobbytypeRepository: HobbytypeRepository,
                      private val userPrivacystatusRepository: UserPrivacystatusRepository,
                      private val userRatingRepository: UserRatingRepository,
                      private val userEventRepository: UserEventRepository): UserService {
    override fun findById(id: Int): Optional<UserEntity> {
        return userRepository.findById(id)
    }

    override fun findAll(): List<UserEntity> {
        return userRepository.findAll()
    }

    override fun findByEmail(email: String): UserEntity? {
        return userRepository.findByEmail(email)
    }

    override fun findAllByLogin(login: String): List<UserEntity> {
        return userRepository.findAllByLogin(login)
    }

    override fun deleteByEmail(email: String) {
        userRepository.deleteByEmail(email)
    }

    override fun save(userEntity: UserEntity) {
        userRepository.save(userEntity)
    }

    override fun firstsave(userEntity: UserEntity) {
        userEntity.roleid = roleRepository.findByName("ROLE_USER")
        userEntity.accountStatusid = accountStatusRepository.findByName("ACTIVE")
        userEntity.userLanguageid = userLanguageRepository.findByName("RUS")
        userEntity.userColorid = userColorRepository.findByName("LIGHT")
        userEntity.password = passwordEncoder.encode(userEntity.password)
        userEntity.hobbytypeid = hobbytypeRepository.findByName("NOTHING")
        userEntity.privacystatusid = userPrivacystatusRepository.findByName("ALL")


////        val friends = Friends()
////        friendsRepository.save(friends)
////
//        userEntity.friendsid = friendsRepository.findById(0).get()
////
////        val  userRating = UserRating(10, arrayOf(), arrayOf())
////        userRatingRepository.save(userRating)
//        userEntity.ratingid = userRatingRepository.findById(0).get()
////
////        val userEvent = UserEvent(arrayOf(), arrayOf(), arrayOf(), arrayOf())
////        userEventRepository.save(userEvent)
//        userEntity.userEvents = userEventRepository.findById(0).get()

        userRepository.save(userEntity)
    }
    override fun findByEmailAndPassword(email: String, password: String): UserEntity? {
        val userEntity: UserEntity? = userRepository.findByEmail(email)
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.password)) {
                return userEntity
            }
        }
        return null
    }

    override fun friendsSave(friends: Friends) {
        friendsRepository.save(friends)
    }

    override fun userRatingSave(userRating: UserRating) {
        userRatingRepository.save(userRating)
    }

    override fun userEventSave(userEvent: UserEvent) {
        userEventRepository.save(userEvent)
    }


}