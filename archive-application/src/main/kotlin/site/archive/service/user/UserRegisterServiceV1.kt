package site.archive.service.user

import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.common.exception.common.DuplicateResourceException
import site.archive.domain.user.BaseUser
import site.archive.domain.user.PasswordUser
import site.archive.domain.user.UserInfo
import site.archive.domain.user.UserRepository
import site.archive.dto.v1.auth.BasicRegisterCommandV1
import site.archive.dto.v1.auth.OAuthRegisterCommandV1
import site.archive.dto.v1.user.BaseUserDtoV1
import site.archive.service.message.MessagingService

@Service
@Transactional(readOnly = true)
class UserRegisterServiceV1(val userRepository: UserRepository, val messagingService: MessagingService) {

    private val log = LoggerFactory.getLogger(javaClass)


    @Transactional
    fun getOrRegisterUser(registerCommand: BasicRegisterCommandV1): Long {
        val user = userRepository.findByMailAddress(registerCommand.email)
            .orElseGet { registerUser(registerCommand) }
        return user.id
    }

    @Transactional
    fun getOrRegisterUserReturnInfo(registerCommand: BasicRegisterCommandV1): UserInfo {
        val user = userRepository.findByMailAddress(registerCommand.email)
            .orElseGet { registerUser(registerCommand) }
        return user.convertToUserInfo()
    }

    @Transactional
    fun registerUser(registerCommand: BasicRegisterCommandV1): BaseUser {
        try {
            val user = userRepository.save(registerCommand.toUserEntity())
            sendRegisterNotification(registerCommand, user)
            return user
        } catch (e: DataIntegrityViolationException) {
            log.error("[v1] 이메일이 이미 존재합니다.", e)
            throw DuplicateResourceException("이메일이 이미 존재합니다.")
        }
    }

    private fun sendRegisterNotification(registerCommand: BasicRegisterCommandV1, user: BaseUser) {
        if (registerCommand is OAuthRegisterCommandV1) {
            val oauthProvider = registerCommand.provider.registrationId
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), oauthProvider)
        } else {
            messagingService.sendUserRegisterMessage(BaseUserDtoV1.from(user), PasswordUser.PASSWORD_TYPE)
        }
    }

}