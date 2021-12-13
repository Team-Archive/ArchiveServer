package com.depromeet.archive.domain.user.entity;

import com.depromeet.archive.domain.common.BaseTimeEntity;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name= "user_type")
@SQLDelete(sql = "UPDATE user SET is_deleted = true WHERE user_id=?")
@Where(clause = "is_deleted = false")
public abstract class BaseUser extends BaseTimeEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId = null;

    @NonNull
    @Column(name = "mail_address", unique = true)
    private String mailAddress;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    public BaseUser(String mailAddress, UserRole role) {
        this.role = role;
        this.mailAddress = mailAddress;
    }

    public UserInfo getUserInfo() {
        return new UserInfo(mailAddress, role, userId);
    }

}
