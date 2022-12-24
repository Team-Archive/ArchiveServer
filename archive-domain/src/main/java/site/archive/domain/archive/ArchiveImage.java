package site.archive.domain.archive;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.common.BaseTimeEntity;

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