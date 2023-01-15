package site.archive.domain.archive;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.archive.converter.CompanionsConverter;
import site.archive.domain.common.BaseTimeEntity;
import site.archive.domain.like.Like;
import site.archive.domain.user.BaseUser;

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
    @Enumerated(value = EnumType.STRING)
    @Column(name = "cover_image_type")
    private CoverImageType coverImageType;
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
                   CoverImageType coverImageType,
                   List<String> companions,
                   BaseUser author) {
        this.id = id;
        this.name = name;
        this.watchedOn = watchedOn;
        this.emotion = emotion;
        this.mainImage = mainImage;
        this.isPublic = isPublic;
        this.coverImageType = coverImageType;
        this.companions = companions;
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

    public void updateToPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

}