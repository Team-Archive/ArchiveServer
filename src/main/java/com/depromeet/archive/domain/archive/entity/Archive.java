package com.depromeet.archive.domain.archive.entity;

import com.depromeet.archive.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

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

@Entity
@Table(name = "archive")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Where(clause = "is_deleted = false")
public class Archive extends BaseTimeEntity {

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

    @Convert(converter = CompanionsConverter.class)
    @Column(name = "companions")
    private List<String> companions;

    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL)
    private List<ArchiveImage> archiveImages = new ArrayList<>();

    @Builder
    public Archive(String name,
                   LocalDate watchedOn,
                   Emotion emotion,
                   String mainImage,
                   List<String> companions) {
        this.name = name;
        this.watchedOn = watchedOn;
        this.emotion = emotion;
        this.mainImage = mainImage;
        this.companions = companions;
    }

    public void addImage(ArchiveImage archiveImage) {
        this.archiveImages.add(archiveImage);
    }

}