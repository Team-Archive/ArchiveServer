package com.depromeet.archive.domain.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicRegisterCommand {
    private String mailAddress;
    private String userName;
}
