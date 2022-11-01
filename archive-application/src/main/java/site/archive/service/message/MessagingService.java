package site.archive.service.message;

import site.archive.dto.v1.user.BaseUserDtoV1;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDtoV1 user, String registerType);

}
