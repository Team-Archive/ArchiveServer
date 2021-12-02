package com.depromeet.archive.security.permissionhandler;

import com.depromeet.archive.domain.archive.ArchiveRepository;
import com.depromeet.archive.domain.archive.entity.Archive;
import com.depromeet.archive.domain.user.entity.UserRole;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.authorization.ArchivePermissionHandler;
import com.depromeet.archive.security.authorization.permissionhandler.ArchiveAdminOrAuthorChecker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ArchiveAdminOrAuthorCheckerTest {

    @InjectMocks
    private ArchiveAdminOrAuthorChecker handler;
    @Mock
    private ArchiveRepository repository;
    @Mock
    private Archive entityMock;

    private final long POST_ID = 11;

    @Test
    public void checkByAuthor() {
        long authorId = 10, requesterId = 10;
        mockArchiveAuthor(authorId);
        UserInfo requester = createUserInfo(requesterId, UserRole.GENERAL);

        Assertions.assertTrue(handler.checkParam(requester, POST_ID));
    }

    @Test
    public void checkByAdmin() {
        long requesterId = 11;
        UserInfo requester = createUserInfo(requesterId, UserRole.ADMIN);

        Assertions.assertTrue(handler.checkParam(requester, POST_ID));
    }

    @Test
    public void checkByNonAuthor() {
        long authorId = 10, requesterId = 11;
        mockArchiveAuthor(authorId);
        UserInfo requester = createUserInfo(requesterId, UserRole.GENERAL);

        Assertions.assertFalse(handler.checkParam(requester, POST_ID));
    }

    private void mockArchiveAuthor(long authorId) {
        Mockito.when(repository.getById(POST_ID)).thenReturn(entityMock);
        Mockito.when(entityMock.getAuthorId()).thenReturn(authorId);
    }



    private UserInfo createUserInfo(long id, UserRole userRole) {
        return UserInfo.builder()
                .userRole(userRole)
                .userId(id)
                .mailAddress("testMail@naver.com")
                .build();
    }

}