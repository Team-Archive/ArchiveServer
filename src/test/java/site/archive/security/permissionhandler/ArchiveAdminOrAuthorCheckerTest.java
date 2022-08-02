package site.archive.security.permissionhandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.user.entity.BaseUser;
import site.archive.domain.user.entity.UserRole;
import site.archive.domain.user.info.UserInfo;
import site.archive.security.authz.ArchiveAdminOrAuthorChecker;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ArchiveAdminOrAuthorCheckerTest {

    private final long POST_ID = 11;
    @InjectMocks
    private ArchiveAdminOrAuthorChecker handler;

    @Mock
    private ArchiveRepository archiveRepository;

    @Mock
    private Archive archive;

    @Test
    void checkByAuthor() {
        var requesterId = 10L;
        var requester = createUserInfo(requesterId, UserRole.GENERAL);

        given(archiveRepository.findById(POST_ID)).willReturn(Optional.of(archive));
        given(archive.getAuthor()).willReturn(new BaseUser(requesterId));

        assertThat(handler.checkParam(requester, POST_ID)).isTrue();
    }

    @Test
    void checkByAdmin() {
        var requesterId = 11;
        var requester = createUserInfo(requesterId, UserRole.ADMIN);
        assertThat(handler.checkParam(requester, POST_ID)).isTrue();
    }

    @Test
    void checkByNonAuthor() {
        var realAuthorId = 100L;
        var requesterId = 10L;
        var requester = createUserInfo(requesterId, UserRole.GENERAL);

        given(archiveRepository.findById(POST_ID)).willReturn(Optional.of(archive));
        given(archive.getAuthor()).willReturn(new BaseUser(realAuthorId));

        assertThat(handler.checkParam(requester, POST_ID)).isFalse();
    }

    private UserInfo createUserInfo(long id, UserRole userRole) {
        return UserInfo.builder()
                       .userRole(userRole)
                       .userId(id)
                       .mailAddress("testMail@naver.com")
                       .build();
    }

}