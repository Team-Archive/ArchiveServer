package site.archive.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

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

    public PasswordUser(String mailAddress, UserRole role, String password) {
        super(mailAddress, role);
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
