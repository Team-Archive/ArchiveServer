package site.archive.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "oauth_user")
@DiscriminatorValue(OAuthUser.OAUTH_TYPE)
@NoArgsConstructor
public class OAuthUser extends BaseUser {

    public static final String OAUTH_TYPE = "oauth";

    @Getter
    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    public OAuthUser(String mailAddress, UserRole role, OAuthProvider provider, String nickname) {
        super(mailAddress, role, nickname);
        this.oAuthProvider = provider;
    }

    @Override
    public String getUserType() {
        return this.getOAuthProvider().getRegistrationId();
    }

}
