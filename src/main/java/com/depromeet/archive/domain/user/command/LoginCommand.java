package com.depromeet.archive.domain.user.command;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCommand {
    private String mailAddress;
    private String password;
}
