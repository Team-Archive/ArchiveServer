package com.depromeet.archive.api.dto.user;

import com.depromeet.archive.domain.user.entity.BaseUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class BaseUserDto {

    private final Long userId;
    private final String mailAddress;
    private final LocalDateTime createdAt;

    public static BaseUserDto from(BaseUser baseUser) {
        return new BaseUserDto(baseUser.getUserId(), baseUser.getMailAddress(), baseUser.getCreatedAt());
    }

}
