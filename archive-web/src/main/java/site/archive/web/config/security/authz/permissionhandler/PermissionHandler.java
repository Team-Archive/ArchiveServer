package site.archive.web.config.security.authz.permissionhandler;

import site.archive.domain.user.UserInfo;

public interface PermissionHandler {

    boolean checkParam(UserInfo requester, Object id);

    boolean checkParam(UserInfo requester);

}
