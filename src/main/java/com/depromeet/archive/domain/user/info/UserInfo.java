package com.depromeet.archive.domain.user.info;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.*;

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
