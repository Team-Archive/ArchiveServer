package site.archive.domain.banner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "banner")
@SQLDelete(sql = "UPDATE banner SET is_deleted = true WHERE banner_id=?")
@Where(clause = "is_deleted = false")
public class Banner extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banner_id")
    private Long id;

    @Column(name = "summary_image")
    private String summaryImage;

    @Column(name = "main_content")
    private String mainContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private BannerType type;

    public Banner(String summaryImage, String mainContent, BannerType type) {
        this.summaryImage = summaryImage;
        this.mainContent = mainContent;
        this.type = type;
    }

}
