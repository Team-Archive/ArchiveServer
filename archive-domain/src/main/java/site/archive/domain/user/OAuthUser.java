package site.archive.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_user")
@DiscriminatorValue("oauth")
@NoArgsConstructor
public class OAuthUser extends BaseUser {

    @Getter
    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    public OAuthUser(String mailAddress, String nickname, UserRole role, OAuthProvider provider) {
        super(mailAddress, nickname, role);
        this.oAuthProvider = provider;
    }
}
