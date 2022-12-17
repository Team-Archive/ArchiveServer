package site.archive.web.config.security.authz;

import site.archive.domain.user.UserInfo;
import site.archive.domain.user.UserRole;
import site.archive.web.config.security.authz.permissionhandler.PermissionHandler;

public class AdminChecker implements PermissionHandler {

    @Override
    public boolean checkParam(UserInfo requester, Object id) {
        return checkParam(requester);
    }

    @Override
    public boolean checkParam(UserInfo requester) {
        return requester.getUserRole() == UserRole.ADMIN;
    }

}
