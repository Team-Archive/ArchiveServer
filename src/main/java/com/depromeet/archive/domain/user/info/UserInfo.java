package com.depromeet.archive.domain.user.info;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    private String mailAddress;
    private UserRole userRole;
    private long userId;
    private String userName;
}
