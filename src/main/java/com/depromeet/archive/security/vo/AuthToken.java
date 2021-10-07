package com.depromeet.archive.security.vo;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {
    private long userId;
    private String mailAddress;
    private UserRole userRole;
    private String userName;
}
