package com.depromeet.archive.domain.user.command;

import com.depromeet.archive.domain.user.entity.BaseUser;
import com.depromeet.archive.domain.user.entity.OAuthProvider;
import com.depromeet.archive.domain.user.entity.OAuthUser;
import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OAuthRegisterCommand extends BasicRegisterCommand {

    private OAuthProvider provider;

    public OAuthRegisterCommand(String mailAddress, OAuthProvider provider) {
        super(mailAddress);
        this.provider = provider;
    }

    public BaseUser toUserEntity() {
        return new OAuthUser(getEmail(), UserRole.GENERAL, provider);
    }
}
