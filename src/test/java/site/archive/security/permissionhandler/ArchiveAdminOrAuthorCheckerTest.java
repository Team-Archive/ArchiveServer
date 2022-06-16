package site.archive.security.permissionhandler;

import org.junit.jupiter.api.Assertions;
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
import site.archive.security.authorization.permissionhandler.ArchiveAdminOrAuthorChecker;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArchiveAdminOrAuthorCheckerTest {

    private final long POST_ID = 11;
    @InjectMocks
    private ArchiveAdminOrAuthorChecker handler;

    @Mock
    private ArchiveRepository archiveRepository;

    @Mock
    private Archive entityMock;

    @Test
    void checkByAuthor() {
        long authorId = 10, requesterId = 10;
        mockArchiveAuthor(authorId);
        UserInfo requester = createUserInfo(requesterId, UserRole.GENERAL);

        Assertions.assertTrue(handler.checkParam(requester, POST_ID));
    }

    @Test
    void checkByAdmin() {
        long requesterId = 11;
        UserInfo requester = createUserInfo(requesterId, UserRole.ADMIN);

        Assertions.assertTrue(handler.checkParam(requester, POST_ID));
    }

    @Test
    void checkByNonAuthor() {
        long authorId = 10, requesterId = 11;
        mockArchiveAuthor(authorId);
        UserInfo requester = createUserInfo(requesterId, UserRole.GENERAL);

        Assertions.assertFalse(handler.checkParam(requester, POST_ID));
    }

    private void mockArchiveAuthor(long authorId) {
        when(archiveRepository.getById(POST_ID)).thenReturn(entityMock);
        when(entityMock.getAuthor()).thenReturn(new BaseUser(authorId, "", UserRole.GENERAL));
    }


    private UserInfo createUserInfo(long id, UserRole userRole) {
        return UserInfo.builder()
                       .userRole(userRole)
                       .userId(id)
                       .mailAddress("testMail@naver.com")
                       .build();
    }

}