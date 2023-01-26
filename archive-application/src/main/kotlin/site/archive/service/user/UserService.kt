package site.archive.service.user

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.common.exception.common.DuplicateFieldValueException
import site.archive.common.exception.common.ResourceNotFoundException
import site.archive.domain.user.UserRepository
import site.archive.dto.v1.user.BaseUserDtoV1
import site.archive.dto.v1.user.SpecificUserDtoV1

@Service
@Transactional(readOnly = true)
class UserService(val userRepository: UserRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun findUserById(userId: Long): BaseUserDtoV1 {
        return userRepository.findById(userId)
            .map(BaseUserDtoV1::from)
            .orElseThrow { ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다.") }
    }

    fun findUserByEmail(email: String): BaseUserDtoV1 {
        return userRepository.findByMailAddress(email)
            .map(BaseUserDtoV1::from)
            .orElseThrow { ResourceNotFoundException("가입되지 않은 Email 입니다.") }
    }

    fun findSpecificUserById(userId: Long): SpecificUserDtoV1 {
        val user = userRepository.findById(userId)
            .orElseThrow { ResourceNotFoundException("아이디에 해당하는 유저가 존재하지 않습니다.") }
        return SpecificUserDtoV1.from(user)
    }

    fun existsEmail(email: String): Boolean {
        return userRepository.findByMailAddress(email).isPresent
    }

    fun existsNickname(nickname: String): Boolean {
        return userRepository.findByNickname(nickname).isPresent
    }

    @Transactional
    fun deleteUser(userId: Long) {
        log.info("User($userId)이 탈퇴했습니다")
        userRepository.deleteById(userId)
    }

    @Transactional
    fun updateUserNickname(userId: Long, nickname: String) {
        if (existsNickname(nickname)) {
            throw DuplicateFieldValueException("nickname", nickname)
        }
        userRepository.updateNickName(userId, nickname)
    }

}