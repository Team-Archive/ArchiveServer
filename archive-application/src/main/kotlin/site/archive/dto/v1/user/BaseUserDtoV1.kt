package site.archive.dto.v1.user

import site.archive.common.dateTimeFormatter
import site.archive.domain.user.BaseUser
import site.archive.domain.user.UserRole
import java.time.LocalDateTime

data class BaseUserDtoV1(
    val userId: Long,
    val mailAddress: String,
    val userRole: UserRole,
    val profileImage: String,
    val nickname: String,
    private val createdAt: LocalDateTime
) {

    fun getCreatedAt(): String {
        return dateTimeFormatter.format(createdAt)
    }

    companion object {
        @JvmStatic
        fun from(baseUser: BaseUser): BaseUserDtoV1 {
            return BaseUserDtoV1(
                userId = baseUser.id,
                mailAddress = baseUser.mailAddress,
                userRole = baseUser.role,
                createdAt = baseUser.createdAt,
                profileImage = baseUser.profileImage,
                nickname = baseUser.nickname
            )
        }
    }

}