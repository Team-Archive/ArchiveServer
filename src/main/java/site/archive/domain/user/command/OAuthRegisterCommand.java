package site.archive.domain.user.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.user.entity.BaseUser;
import site.archive.domain.user.entity.OAuthProvider;
import site.archive.domain.user.entity.OAuthUser;
import site.archive.domain.user.entity.UserRole;

@NoArgsConstructor
@Getter
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
