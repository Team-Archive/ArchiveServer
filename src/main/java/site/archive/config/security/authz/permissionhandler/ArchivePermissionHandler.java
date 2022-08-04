package site.archive.config.security.authz.permissionhandler;

import site.archive.domain.user.info.UserInfo;

public interface ArchivePermissionHandler {
    boolean checkParam(UserInfo requester, Object id);

}
