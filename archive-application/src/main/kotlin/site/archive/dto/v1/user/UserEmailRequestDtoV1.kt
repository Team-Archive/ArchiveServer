package site.archive.dto.v1.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UserEmailRequestDtoV1(
    @field: NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @field: Email(message = "올바른 이메일을 입력해 주세요.")
    val email: String
)