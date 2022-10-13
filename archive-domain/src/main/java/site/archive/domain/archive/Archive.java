package site.archive.domain.archive;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.archive.converter.CompanionsConverter;
import site.archive.domain.common.BaseTimeEntity;
import site.archive.domain.like.Like;
import site.archive.domain.report.Report;
import site.archive.domain.user.BaseUser;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "archive")
@SQLDelete(sql = "UPDATE archive SET is_deleted = true WHERE archive_id=?")
@Where(clause = "is_deleted = false")
public class Archive extends BaseTimeEntity {

    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL)
    private final List<Like> likes = new ArrayList<>();
    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL)
    private final List<ArchiveImage> archiveImages = new ArrayList<>();
    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL)
    private final List<Report> reports = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "archive_id")
    private Long id;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private BaseUser author;
    @Convert(converter = CompanionsConverter.class)
    @Column(name = "companions")
    private List<String> companions;

    public Archive(Long id) {
        this.id = id;
    }

    @Builder
    public Archive(Long id,
                   String name,
                   LocalDate watchedOn,
                   Emotion emotion,
                   String mainImage,
                   Boolean isPublic,
                   List<String> companions,
                   BaseUser author) {
        this.id = id;
        this.name = name;
        this.watchedOn = watchedOn;
        this.emotion = emotion;
        this.mainImage = mainImage;
        this.companions = companions;
        this.isPublic = isPublic;
        this.author = author;
    }

    public void addImage(ArchiveImage archiveImage) {
        this.archiveImages.add(archiveImage);
    }

    public Optional<Like> getLikeByUserId(Long userId) {
        return likes.stream()
                    .filter(like -> Objects.equals(like.getUser().getId(), userId))
                    .findFirst();
    }

}