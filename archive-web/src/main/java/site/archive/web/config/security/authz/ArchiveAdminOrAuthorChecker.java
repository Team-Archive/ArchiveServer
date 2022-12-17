package site.archive.web.config.security.authz;

import lombok.RequiredArgsConstructor;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;
import site.archive.service.archive.ArchiveService;
import site.archive.web.config.security.authz.permissionhandler.ArchivePermissionHandler;

@RequiredArgsConstructor
public class ArchiveAdminOrAuthorChecker implements ArchivePermissionHandler {

    private final ArchiveService archiveService;

    /*
    권한 문제는 Service 단에서 처리하는게 Query를 1번으로 수행할 수 있어 더 효율적
     */
    @Override
    public boolean checkParam(UserInfo requester, Object id) {
        if (!(id instanceof Long)) {
            throw new IllegalArgumentException("id 값은 long 타입이여야 합니다");
        }

        if (requester.getUserRole() == UserRole.ADMIN) {
            return true;
        }

        return archiveService.getArchiveAuthorId((Long) id)
                             .filter(authorId -> authorId == requester.getUserId())
                             .isPresent();
    }

    @Override
    public boolean checkParam(UserInfo requester) {
        return false;
    }

}
