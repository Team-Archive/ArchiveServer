package site.archive.security.permissionhandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;
import site.archive.service.archive.ArchiveService;
import site.archive.web.config.security.authz.AdminChecker;
import site.archive.web.config.security.authz.AdminOrAuthorChecker;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class AdminOrAuthorCheckerTest {

    private static final long POST_ID = 11;

    private AdminOrAuthorChecker adminOrAuthorChecker;
    private AdminChecker adminChecker;
    private ArchiveService archiveService;

    @BeforeEach
    void setUp() {
        archiveService = mock(ArchiveService.class);
        adminOrAuthorChecker = new AdminOrAuthorChecker(archiveService);
        adminChecker = new AdminChecker();
    }

    @DisplayName("어드민은 아니고 저자인 경우, Permission 검증")
    @Test
    void checkByAuthor() {
        // given
        var requesterId = 10L;
        var requester = createUserInfo(requesterId, UserRole.GENERAL);

        given(archiveService.getArchiveAuthorId(POST_ID)).willReturn(Optional.of(requesterId));

        // when & then
        assertThat(adminOrAuthorChecker.checkParam(requester, POST_ID)).isTrue();
        assertThat(adminChecker.checkParam(requester)).isFalse();
    }

    @DisplayName("어드민 일 때, Permission 검증")
    @Test
    void checkByAdmin() {
        // given
        var requesterId = 11;
        var requester = createUserInfo(requesterId, UserRole.ADMIN);

        // when & then
        assertThat(adminOrAuthorChecker.checkParam(requester, POST_ID)).isTrue();
        assertThat(adminChecker.checkParam(requester)).isTrue();
    }


    @DisplayName("어드민/저자가 아닐 때, Permission 검증")
    @Test
    void checkByNonAuthorNonAdmin() {
        // given
        var realAuthorId = 100L;
        var requesterId = 10L;
        var requester = createUserInfo(requesterId, UserRole.GENERAL);

        given(archiveService.getArchiveAuthorId(POST_ID)).willReturn(Optional.of(realAuthorId));

        // when & then
        assertThat(adminOrAuthorChecker.checkParam(requester, POST_ID)).isFalse();
        assertThat(adminChecker.checkParam(requester)).isFalse();
    }

    private UserInfo createUserInfo(long id, UserRole userRole) {
        return UserInfo.builder().userRole(userRole).userId(id).mailAddress("testMail@naver.com").build();
    }

}