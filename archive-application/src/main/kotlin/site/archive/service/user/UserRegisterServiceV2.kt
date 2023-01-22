package site.archive.service.user

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.common.exception.common.DuplicateResourceException
import site.archive.domain.user.PasswordUser
import site.archive.domain.user.UserInfo
import site.archive.domain.user.UserRepository
import site.archive.dto.v1.user.BaseUserDtoV1
import site.archive.dto.v2.OAuthRegisterRequestDto
import site.archive.dto.v2.PasswordRegisterRequestDto
import site.archive.service.message.MessagingService

@Service
@Transactional(readOnly = true)
class UserRegisterServiceV2(val userRepository: UserRepository, val messagingService: MessagingService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Transactional
    fun registerUser(registerRequest: PasswordRegisterRequestDto): UserInfo {
        try {
            val user = userRepository.save(registerRequest.toUserEntity())
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), PasswordUser.PASSWORD_TYPE)
            return user.convertToUserInfo()
        } catch (e: DataIntegrityViolationException) {
            log.error("[v2] 이메일 또는 닉네임이 중복되었습니다.", e)
            throw DuplicateResourceException("이메일 또는 닉네임이 중복되었습니다. 중복을 다시 확인해주세요.")
        }
    }

    @Transactional
    fun registerUser(registerCommand: OAuthRegisterRequestDto): UserInfo {
        try {
            val user = userRepository.save(registerCommand.toUserEntity())
            val oAuthProvider = registerCommand.provider.registrationId
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), oAuthProvider)
            return user.convertToUserInfo()
        } catch (e: DataIntegrityViolationException) {
            log.error("[v2] 이메일 또는 닉네임이 중복되었습니다.", e)
            throw DuplicateResourceException("이메일 또는 닉네임이 중복되었습니다. 중복을 다시 확인해주세요.")
        }
    }

}