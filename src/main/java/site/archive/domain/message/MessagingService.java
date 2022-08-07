package site.archive.domain.message;

import site.archive.api.v1.dto.user.BaseUserDto;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDto user, String registerType);

}
