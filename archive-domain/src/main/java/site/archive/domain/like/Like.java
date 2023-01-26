package site.archive.domain.like;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import site.archive.domain.archive.Archive;
import site.archive.domain.common.BaseTimeEntity;
import site.archive.domain.user.BaseUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "archive_like")
@SQLDelete(sql = "UPDATE archive_like SET is_deleted = true WHERE archive_like_id=?")
public class Like extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public static Like of(final Long userId, final Long archiveId) {
        var user = new BaseUser(userId);
        var archive = new Archive(archiveId);
        return new Like(archive, user);
    }

}
