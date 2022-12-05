package site.archive.dto.v2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.archive.domain.user.BaseUser;
import site.archive.domain.user.OAuthProvider;
import site.archive.domain.user.OAuthUser;
import site.archive.domain.user.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthRegisterRequestDto {

    private OAuthProvider provider;
    private String email;
    private String nickname;

    public BaseUser toUserEntity() {
        return new OAuthUser(this.email,
                             UserRole.GENERAL,
                             this.provider,
                             this.nickname);
    }

}