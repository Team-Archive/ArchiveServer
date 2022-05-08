package com.depromeet.archive.domain.user.info;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {

    private String mailAddress;
    private UserRole userRole;
    private long userId;

}
