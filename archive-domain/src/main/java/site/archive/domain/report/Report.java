package site.archive.domain.report;

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
@Table(name = "archive_report")
@SQLDelete(sql = "UPDATE archive_report SET is_deleted = true WHERE archive_report_id=?")
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_report_id")
    private Long id;

    @Column(name = "reason")
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id", nullable = false)
    private Archive archive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private BaseUser user;

    public Report(String reason, Archive archive, BaseUser user) {
        this.reason = reason;
        this.archive = archive;
        this.user = user;
    }

    public static Report of(String reason, Long archiveId, Long userId) {
        var archive = new Archive(archiveId);
        var user = new BaseUser(userId);
        return new Report(reason, archive, user);
    }

}
