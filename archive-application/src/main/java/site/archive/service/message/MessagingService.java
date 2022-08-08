package site.archive.service.message;

import site.archive.dto.v1.user.BaseUserDto;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDto user, String registerType);

}
