package site.archive.domain.common;

import site.archive.api.v1.dto.user.BaseUserDto;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDto user, String registerType);

}
