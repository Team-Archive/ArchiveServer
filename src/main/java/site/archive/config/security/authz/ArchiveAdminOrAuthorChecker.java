package site.archive.config.security.authz;

import lombok.RequiredArgsConstructor;
import site.archive.config.security.authz.permissionhandler.ArchivePermissionHandler;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;

@RequiredArgsConstructor
public class ArchiveAdminOrAuthorChecker implements ArchivePermissionHandler {

    private final ArchiveRepository archiveRepository;

    // TODO: 권한 문제는 Service 단에서 처리하는게 Query를 1번으로 수행할 수 있어 더 효율적
    @Override
    public boolean checkParam(UserInfo requester, Object id) {
        if (!(id instanceof Long)) {
            throw new IllegalArgumentException("id 값은 long 타입이여야 합니다");
        }

        if (requester.getUserRole() == UserRole.ADMIN) {
            return true;
        }

        return archiveRepository.findById((Long) id)
                                .filter(archive -> archive.getAuthor().getId() == requester.getUserId())
                                .isPresent();
    }

}
