package com.depromeet.archive.security.authorization.permissionhandler;

import com.depromeet.archive.domain.archive.ArchiveRepository;
import com.depromeet.archive.domain.archive.entity.Archive;
import com.depromeet.archive.domain.user.entity.UserRole;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.authorization.ArchivePermissionHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ArchiveAdminOrAuthorChecker implements ArchivePermissionHandler {

    private final ArchiveRepository repository;

    @Override
    public boolean checkParam(UserInfo requester, Object id) {
        if (!(id instanceof Long))
            throw new IllegalStateException("id 값은 long 타입이여야 합니다");
        long rawId = (Long) id;
        if (requester.getUserRole() == UserRole.ADMIN)
            return true;
        Archive archiveToCheck = repository.getById(rawId);
        return archiveToCheck.getAuthorId() == requester.getUserId();
    }

    @Override
    public boolean checkReturn(UserInfo requester, Object ret) {
        return true;
    }
}
