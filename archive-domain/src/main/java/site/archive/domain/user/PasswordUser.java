package site.archive.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "password_user")
@DiscriminatorValue(PasswordUser.PASSWORD_TYPE)
@DynamicInsert
@NoArgsConstructor
public class PasswordUser extends BaseUser {

    public static final String PASSWORD_TYPE = "password";

    @Getter
    @Column(name = "password")
    private String password;

    @Column(name = "is_temporary_password", columnDefinition = "boolean default false")
    private Boolean isTemporaryPassword;

    public PasswordUser(String mailAddress, UserRole role, String password, String nickname) {
        super(mailAddress, role, nickname);
        this.password = password;
    }

    public void updatePassword(final String password, final boolean isTemporaryPassword) {
        this.password = password;
        this.isTemporaryPassword = isTemporaryPassword;
    }

    public boolean isCurrentTemporaryPassword() {
        return this.isTemporaryPassword;
    }

}
