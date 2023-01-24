package site.archive.dto.v1.user

import site.archive.common.dateTimeFormatter
import site.archive.domain.user.BaseUser
import site.archive.domain.user.UserRole

data class SpecificUserDtoV1(
    val userId: Long?,
    val mailAddress: String,
    val userRole: UserRole,
    val createdAt: String,
    val profileImage: String,
    val nickname: String,
    val userType: String
) {

    companion object {
        @JvmStatic
        fun from(baseUser: BaseUser) : SpecificUserDtoV1 {
            val createdAt = dateTimeFormatter.format(baseUser.createdAt)
            return SpecificUserDtoV1(
                userId = baseUser.id,
                mailAddress = baseUser.mailAddress,
                userRole = baseUser.role,
                createdAt = createdAt,
                profileImage = baseUser.profileImage,
                nickname = baseUser.nickname,
                userType = baseUser.userType
            )
        }
    }

}