package site.archive.dto.v1.auth

import site.archive.common.extractIdFromMail
import site.archive.domain.user.BaseUser
import site.archive.domain.user.OAuthProvider
import site.archive.domain.user.OAuthUser
import site.archive.domain.user.UserRole

class OAuthRegisterCommandV1(email: String, val provider: OAuthProvider) : BasicRegisterCommandV1(email) {

    override fun toUserEntity(): BaseUser {
        return OAuthUser(
            email,
            UserRole.GENERAL,
            provider,
            extractIdFromMail(email)
        )
    }

}
