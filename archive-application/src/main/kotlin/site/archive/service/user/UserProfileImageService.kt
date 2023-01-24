package site.archive.service.user

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.domain.user.UserRepository

@Service
@Transactional(readOnly = true)
class UserProfileImageService(val userRepository: UserRepository) {

    @Transactional
    fun updateUserProfileImage(userId: Long, profileImageUri: String) {
        userRepository.updateUserProfileImage(userId, profileImageUri)
    }

}