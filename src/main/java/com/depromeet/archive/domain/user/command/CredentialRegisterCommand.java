package com.depromeet.archive.domain.user.command;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class CredentialRegisterCommand extends BasicRegisterCommand {
    private String credential;

    public CredentialRegisterCommand(String userMail, String userName, String credential) {
        super(userMail, userName);
        this.credential = credential;
    }

}
