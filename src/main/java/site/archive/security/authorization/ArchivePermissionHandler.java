package site.archive.security.authorization;

import site.archive.domain.user.info.UserInfo;

public interface ArchivePermissionHandler {
    boolean checkParam(UserInfo requester, Object id);

    boolean checkReturn(UserInfo requester, Object ret);
}
