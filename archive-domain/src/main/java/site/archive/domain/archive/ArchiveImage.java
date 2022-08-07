package site.archive.domain.archive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.common.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "archive_image")
@SQLDelete(sql = "UPDATE archive_image SET is_deleted = true WHERE archive_image_id=?")
@Where(clause = "is_deleted = false")
public class ArchiveImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_image_id")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "review")
    private String review;

    @Column(name = "background_color")
    private String backgroundColor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "archive_id")
    private Archive archive;

    public ArchiveImage(String image, String review, String backgroundColor, Archive archive) {
        this.image = image;
        this.review = review;
        this.backgroundColor = backgroundColor;
        this.archive = archive;
    }

}