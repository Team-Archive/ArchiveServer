package site.archive.domain.archive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import site.archive.domain.JpaTestSupport;
import site.archive.domain.archive.custom.ArchiveCommunityTimeSortType;
import site.archive.domain.archive.custom.ArchivePageable;
import site.archive.domain.like.LikeRepository;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("JpaIntegrationTest")
class ArchiveRepositoryPaginationTest extends JpaTestSupport {

    private static final int TEST_PAGE_ELEMENT_SIZE = 3;

    @Autowired
    ArchiveRepository archiveRepository;

    @Autowired
    LikeRepository likeRepository;

    @Test
    @DisplayName("Like의 개수가 올바르게 반환되는지 확인한다")
    void likeCountTest() {
        // given
        var timeSortType = ArchiveCommunityTimeSortType.CREATED_AT;
        var archivePageable = new ArchivePageable(timeSortType, null, null, null);

        // when
        var firstPageArchives = archiveRepository.findFirstPageOnlyPublic(archivePageable, TEST_PAGE_ELEMENT_SIZE);

        // then
        firstPageArchives.forEach(archive -> {
            var likes = likeRepository.findByArchiveId(archive.getId());
            var likeCount = archive.getLikes().size();
            assertThat(likes).hasSize(likeCount);
        });
    }

    @Test
    @DisplayName("설정한 Emotion에 맞게 제대로 필터링이 되었는지 확인한다")
    void emotionFilterTest() {
        // given
        var emotion = Emotion.INTERESTING;
        var timeSortType = ArchiveCommunityTimeSortType.CREATED_AT;
        var archivePageable = new ArchivePageable(timeSortType, emotion, null, null);

        // when
        var firstPageArchives = archiveRepository.findFirstPageOnlyPublic(archivePageable, TEST_PAGE_ELEMENT_SIZE);

        // then
        firstPageArchives.forEach(
            archive -> assertThat(archive.getEmotion()).isEqualTo(Emotion.INTERESTING));
    }

    @DisplayName("첫 번째 페이지 조회 시, 시간 정렬 타입에 맞게 정렬되어 있는지 확인한다")
    @ParameterizedTest
    @EnumSource(value = ArchiveCommunityTimeSortType.class, names = {"CREATED_AT", "WATCHED_ON"})
    void firstPageTest(ArchiveCommunityTimeSortType timeSortType) {
        // when
        var archivePageable = new ArchivePageable(timeSortType, null, null, null);
        var firstPageArchives = archiveRepository.findFirstPageOnlyPublic(archivePageable, TEST_PAGE_ELEMENT_SIZE);

        // then
        assertThat(firstPageArchives).hasSize(TEST_PAGE_ELEMENT_SIZE);
        IntStream.range(1, TEST_PAGE_ELEMENT_SIZE - 1).forEachOrdered(i -> {
            var firstArchive = firstPageArchives.get(i - 1);
            var nextArchive = firstPageArchives.get(i);
            assertThat(firstArchive.getIsPublic()).isTrue();
            assertThat(firstArchive.getIsDeleted()).isFalse();

            if (timeSortType == ArchiveCommunityTimeSortType.CREATED_AT) {
                if (firstArchive.getCreatedAt().isEqual(nextArchive.getCreatedAt())) {
                    assertThat(firstArchive.getId() > nextArchive.getId()).isTrue();
                } else {
                    assertThat(firstArchive.getCreatedAt().isAfter(nextArchive.getCreatedAt())).isTrue();
                }
            } else {
                if (firstArchive.getWatchedOn().isEqual(nextArchive.getWatchedOn())) {
                    assertThat(firstArchive.getId() > nextArchive.getId()).isTrue();
                } else {
                    assertThat(firstArchive.getWatchedOn().isAfter(nextArchive.getWatchedOn())).isTrue();
                }
            }
        });
    }

    @DisplayName("두 번째 페이지 조회 시, 시간 정렬 타입에 맞게 정렬되어 있는지 확인하고 첫 번째 페이지와 비교하여 정렬이 제대로 되었는지 확인한다")
    @ParameterizedTest
    @EnumSource(value = ArchiveCommunityTimeSortType.class, names = {"CREATED_AT", "WATCHED_ON"})
    void nextPageTest(ArchiveCommunityTimeSortType timeSortType) {
        // given
        var archivePageable = new ArchivePageable(timeSortType, null, null, null);
        var firstPageArchives = archiveRepository.findFirstPageOnlyPublic(archivePageable, TEST_PAGE_ELEMENT_SIZE);
        var lastArchiveOfFirstPage = firstPageArchives.get(TEST_PAGE_ELEMENT_SIZE - 1);
        var lastArchiveMilli = timeSortType.convertToMillis(lastArchiveOfFirstPage);

        // when
        var archiveNextPageable = new ArchivePageable(timeSortType, null, lastArchiveMilli, lastArchiveOfFirstPage.getId());
        var nextPageArchives = archiveRepository.findNextPageOnlyPublic(archiveNextPageable, TEST_PAGE_ELEMENT_SIZE);

        // then
        nextPageArchives.forEach(archive -> {
            assertThat(archive.getIsPublic()).isTrue();
            assertThat(archive.getIsDeleted()).isFalse();

            if (timeSortType == ArchiveCommunityTimeSortType.CREATED_AT) {
                if (lastArchiveOfFirstPage.getCreatedAt().isEqual(archive.getCreatedAt())) {
                    assertThat(lastArchiveOfFirstPage.getId() > archive.getId()).isTrue();
                } else {
                    assertThat(lastArchiveOfFirstPage.getCreatedAt().isAfter(archive.getCreatedAt())).isTrue();
                }
            } else {
                if (lastArchiveOfFirstPage.getWatchedOn().isEqual(archive.getWatchedOn())) {
                    assertThat(lastArchiveOfFirstPage.getId() > archive.getId()).isTrue();
                } else {
                    assertThat(lastArchiveOfFirstPage.getWatchedOn().isAfter(archive.getWatchedOn())).isTrue();
                }
            }
        });
    }

}
