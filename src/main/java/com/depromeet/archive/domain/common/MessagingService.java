package com.depromeet.archive.domain.common;

import com.depromeet.archive.api.dto.user.BaseUserDto;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUserDto baseUserDto);

}
