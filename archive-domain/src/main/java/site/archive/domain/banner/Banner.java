package site.archive.domain.banner;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import site.archive.domain.common.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
