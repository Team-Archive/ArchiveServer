package site.archive.security.authorization.permissionhandler;

import lombok.RequiredArgsConstructor;
import site.archive.domain.archive.ArchiveRepository;
import site.archive.domain.archive.entity.Archive;
import site.archive.domain.user.entity.UserRole;
import site.archive.domain.user.info.UserInfo;
import site.archive.security.authorization.ArchivePermissionHandler;

@RequiredArgsConstructor
public class ArchiveAdminOrAuthorChecker implements ArchivePermissionHandler {

    private final ArchiveRepository repository;

    @Override
    public boolean checkParam(UserInfo requester, Object id) {
        if (!(id instanceof Long)) {throw new IllegalStateException("id 값은 long 타입이여야 합니다");}
        long rawId = (Long) id;
        if (requester.getUserRole() == UserRole.ADMIN) {return true;}
        Archive archiveToCheck = repository.getById(rawId);
        return archiveToCheck.getAuthor().getId() == requester.getUserId();
    }

    @Override
    public boolean checkReturn(UserInfo requester, Object ret) {
        return true;
    }
}
