package site.archive.domain.archive;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.archive.entity.Emotion;
import site.archive.domain.user.entity.UserRole;
import site.archive.domain.user.info.UserInfo;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArchiveServiceTest {

    @Mock
    ArchiveRepository archiveRepository;

    private static final long USER_ID = 1L;
    private ArchiveService archiveService;

    @BeforeEach
    void setUp() {
        archiveService = new ArchiveService(archiveRepository);
    }

    @Test
    void 특정_USER의_모든_Archive를_가져온다() {
        // given
        var userInfo = new UserInfo("dummy@test.com", UserRole.GENERAL, USER_ID);
        var archives = dummyArchives(USER_ID);
        given(archiveRepository.findAllByAuthorId(USER_ID)).willReturn(archives);

        // when
        var allArchives = archiveService.getAllArchive(userInfo);

        // then
        assertThat(allArchives.getArchiveCount()).isEqualTo(archives.size());
        allArchives.getArchives()
                   .forEach(archiveDto -> assertThat(archiveDto.getAuthorId()).isEqualTo(USER_ID));
    }

    @Test
    void 특정_USER의_모든_Archive를_가져온다2() {
        // given
        var userInfo = new UserInfo("dummy@test.com", UserRole.GENERAL, USER_ID);
        given(archiveRepository.findAllByAuthorId(USER_ID)).willReturn(Collections.emptyList());

        // when
        var allArchives = archiveService.getAllArchive(userInfo);

        // then
        assertThat(allArchives.getArchiveCount()).isZero();
        allArchives.getArchives()
                   .forEach(archiveDto -> assertThat(archiveDto.getAuthorId()).isEqualTo(USER_ID));
    }


    private List<Archive> dummyArchives(long authorId) {
        return List.of(
            Archive.builder()
                   .name("archive_1")
                   .authorId(authorId)
                   .watchedOn(LocalDate.now())
                   .emotion(Emotion.PLEASANT)
                   .mainImage("main_image_1")
                   .companions(Collections.emptyList())
                   .build(),
            Archive.builder()
                   .name("archive_2")
                   .authorId(authorId)
                   .watchedOn(LocalDate.now())
                   .emotion(Emotion.AMAZING)
                   .mainImage("main_image_2")
                   .companions(Collections.emptyList())
                   .build()
        );

    }

}