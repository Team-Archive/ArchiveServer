package site.archive.domain.archive.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.common.BaseTimeEntity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "archive")
@SQLDelete(sql = "UPDATE archive SET is_deleted = true WHERE archive_id=?")
@Where(clause = "is_deleted = false")
public class Archive extends BaseTimeEntity {

    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL)
    private final List<ArchiveImage> archiveImages = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long id;
    @Column(name = "author_id", nullable = false)
    private long authorId;
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    @Column(name = "watched_on", columnDefinition = "TIMESTAMP")
    private LocalDate watchedOn;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "emotion")
    private Emotion emotion;
    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "is_public")
    private Boolean isPublic;

    @Convert(converter = CompanionsConverter.class)
    @Column(name = "companions")
    private List<String> companions;

    @Builder
    public Archive(Long id,
                   String name,
                   long authorId,
                   LocalDate watchedOn,
                   Emotion emotion,
                   String mainImage,
                   Boolean isPublic,
                   List<String> companions) {
        this.id = id;
        this.name = name;
        this.watchedOn = watchedOn;
        this.emotion = emotion;
        this.mainImage = mainImage;
        this.companions = companions;
        this.authorId = authorId;
        this.isPublic = isPublic;
    }

    public void addImage(ArchiveImage archiveImage) {
        this.archiveImages.add(archiveImage);
    }
}