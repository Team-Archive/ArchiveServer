package site.archive.service.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import site.archive.common.exception.common.ResourceNotFoundException
import site.archive.common.exception.common.UnauthorizedResourceException
import site.archive.common.exception.user.LoginFailException
import site.archive.common.exception.user.OAuthUserHasNotPasswordException
import site.archive.domain.user.BaseUser
import site.archive.domain.user.PasswordUser
import site.archive.domain.user.PasswordUserRepository
import site.archive.domain.user.UserInfo
import site.archive.domain.user.UserRepository
import site.archive.dto.v1.auth.LoginCommandV1
import site.archive.dto.v1.user.UserPasswordResetRequestDtoV1
import site.archive.infra.mail.MailService

@Service
@Transactional(readOnly = true)
class UserAuthService(
    val userRepository: UserRepository,
    val passwordUserRepository: PasswordUserRepository,
    val encoder: PasswordEncoder,
    val mailService: MailService
) {

    fun tryLoginAndReturnInfo(command: LoginCommandV1): UserInfo {
        val user = verifyPasswordReturnUser(command.email, command.password)
        return user.convertToUserInfo()
    }

    @Transactional
    fun updateTemporaryPassword(email: String, temporaryPassword: String) {
        val passwordUser = userRepository.findByMailAddress(email)
            .map { convertPasswordUser(it) }
            .orElseThrow { ResourceNotFoundException("Email") }
        passwordUser.updatePassword(encoder.encode(temporaryPassword), true)
        mailService.sendTemporaryPassword(email, temporaryPassword)
    }

    @Transactional
    fun resetPassword(userInfo: UserInfo, userPasswordResetRequestDtoV1: UserPasswordResetRequestDtoV1) {
        if (userInfo.mailAddress != userPasswordResetRequestDtoV1.email) {
            throw UnauthorizedResourceException("해당 이메일에 대한 비밀번호 초기화 권한이 없습니다.")
        }
        this.resetPassword(userPasswordResetRequestDtoV1)
    }

    @Transactional
    fun resetPassword(userPasswordResetRequestDtoV1: UserPasswordResetRequestDtoV1) {
        val passwordUser = verifyPasswordReturnUser(userPasswordResetRequestDtoV1.email, userPasswordResetRequestDtoV1.currentPassword)
        passwordUser.updatePassword(encoder.encode(userPasswordResetRequestDtoV1.newPassword), false)
    }

    fun isTemporaryPasswordLogin(userId: Long): Boolean {
        return passwordUserRepository.findById(userId)
            .map(PasswordUser::isCurrentTemporaryPassword)
            .orElse(false)
    }

    private fun verifyPasswordReturnUser(email: String, password: String): PasswordUser {
        val user = passwordUserRepository.findByMailAddress(email)
            .orElseThrow { ResourceNotFoundException("Email") }
        if (!encoder.matches(password, user.password)) {
            throw LoginFailException("비밀번호가 다릅니다")
        }
        return user
    }

    private fun convertPasswordUser(user: BaseUser): PasswordUser {
        if (user !is PasswordUser) {
            throw OAuthUserHasNotPasswordException()
        }
        return user
    }

}