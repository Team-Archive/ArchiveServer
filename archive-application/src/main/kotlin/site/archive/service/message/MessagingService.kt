package site.archive.service.message

import site.archive.dto.v1.user.BaseUserDtoV1

interface MessagingService {

    fun sendUserRegisterMessage(user: BaseUserDtoV1, registerType: String)

}