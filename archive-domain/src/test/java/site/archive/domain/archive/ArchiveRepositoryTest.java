package site.archive.domain.archive;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import site.archive.common.DateTimeUtil;
import site.archive.domain.JpaTestSupport;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("JpaIntegrationTest")
class ArchiveRepositoryTest extends JpaTestSupport {

    @Autowired
    ArchiveRepository archiveRepository;

    @Test
    @DisplayName("이번 달, 아카이브 게시물 개수를 확인한다")
    void currentMonthCountOfArchiveTest() {
        // given
        var oneAuthorId = 1L;
        var fourAuthorId = 4L;
        var eightAuthorId = 8L;

        // and - set current month to 2022.08
        DateTimeUtil.changeClock(LocalDate.of(2022, 8, 11));

        // when
        var oneAuthorArchiveCount = archiveRepository.countArchiveOfCurrentMonthByAuthorId(oneAuthorId);
        var fourAuthorArchiveCount = archiveRepository.countArchiveOfCurrentMonthByAuthorId(fourAuthorId);
        var eightAuthorArchiveCount = archiveRepository.countArchiveOfCurrentMonthByAuthorId(eightAuthorId);

        // then
        // Note: This expected values depend on resources/archive.sql values.
        assertThat(oneAuthorArchiveCount).isZero();
        assertThat(fourAuthorArchiveCount).isEqualTo(1);
        assertThat(eightAuthorArchiveCount).isEqualTo(1);
    }

}
