package site.archive.domain.like.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.common.BaseTimeEntity;
import site.archive.domain.user.entity.BaseUser;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "archive_like")
@SQLDelete(sql = "UPDATE archive_like SET is_deleted = true WHERE archive_like_id=?")
@Where(clause = "is_deleted = false")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "archive_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    private Archive archive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private BaseUser user;

    public Like(Archive archive, BaseUser user) {
        this.archive = archive;
        this.user = user;
    }

    public static Like of(Long userId, Long archiveId) {
        var user = new BaseUser(userId);
        var archive = new Archive(archiveId);
        return new Like(archive, user);
    }

}
