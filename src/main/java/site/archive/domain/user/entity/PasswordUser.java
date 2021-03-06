package site.archive.domain.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@DiscriminatorValue("password")
@DynamicInsert
@Table(name = "password_user")
public class PasswordUser extends BaseUser {

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
