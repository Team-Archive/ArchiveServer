package site.archive.dto.v1.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class UserPasswordResetRequestDtoV1(
    @field: NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @field: Email(message = "올바른 이메일을 입력해 주세요.")
    val email: String,

    @field: NotBlank(message = "현재 비밀번호는 필수 입력 항목입니다.")
    val currentPassword: String,

    @field: NotBlank(message = "새로운 비밀번호는 필수 입력 항목입니다.")
    @field: Pattern(
        regexp = "(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9@$!%*#?&]{8,20}$",
        message = "비밀번호는 영문/숫자 를 꼭 포함하여 8~20자리로 입력해 주세요."
    )
    val newPassword: String
)