package site.archive.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("oauth")
@NoArgsConstructor
@Table(name = "oauth_user")
public class OAuthUser extends BaseUser {

    @Getter
    @Column(name = "oauth_provider")
    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    public OAuthUser(String mailAddress, UserRole role, OAuthProvider provider) {
        super(mailAddress, role);
        this.oAuthProvider = provider;
    }
}
