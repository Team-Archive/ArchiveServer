package com.depromeet.archive.domain.common;

import com.depromeet.archive.domain.user.entity.BaseUser;

public interface MessagingService {

    void sendUserRegisterMessage(BaseUser baseUser, String registerType);

}
