package com.depromeet.archive.security.result;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private long userId;
    private String mailAddress;
    private UserRole userRole;
    private String userName;

}
