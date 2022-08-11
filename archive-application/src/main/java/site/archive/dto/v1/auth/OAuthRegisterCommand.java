package site.archive.dto.v1.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.OAuthProvider;
import site.archive.domain.user.OAuthUser;
import site.archive.domain.user.UserRole;

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
