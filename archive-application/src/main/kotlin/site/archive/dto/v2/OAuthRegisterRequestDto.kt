package site.archive.dto.v2

import site.archive.domain.user.BaseUser
import site.archive.domain.user.OAuthProvider
import site.archive.domain.user.OAuthUser
import site.archive.domain.user.UserRole

data class OAuthRegisterRequestDto(
    val provider: OAuthProvider,
    val email: String,
    val nickname: String
) {

    fun toUserEntity(): BaseUser {
        return OAuthUser(email, UserRole.GENERAL, provider, nickname)
    }

}