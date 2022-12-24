package site.archive.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.common.ArchiveStringUtils;
import site.archive.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id=?")
@Where(clause = "is_deleted = false")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BaseUser extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @Setter
    private Long id;

    @Column(name = "mail_address", unique = true)
    private String mailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "nickname")
    private String nickname;

    public BaseUser(Long id) {
        this.id = id;
    }

    protected BaseUser(String mailAddress, UserRole role) {
        this.role = role;
        this.mailAddress = mailAddress;
    }

    protected BaseUser(String mailAddress, UserRole role, String nickname) {
        this.role = role;
        this.mailAddress = mailAddress;
        this.nickname = nickname;
    }

    public UserInfo convertToUserInfo() {
        return new UserInfo(mailAddress, role, id);
    }

    public String getNickname() {
        return nickname != null ? nickname : ArchiveStringUtils.extractIdFromMail(mailAddress);
    }

    public String getProfileImage() {
        return profileImage != null ? profileImage : "";
    }

    public String getUserType() {
        return PasswordUser.PASSWORD_TYPE;
    }

}
