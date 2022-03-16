package com.depromeet.archive.security.authorization;

import com.depromeet.archive.domain.user.info.UserInfo;

public interface ArchivePermissionHandler {
    boolean checkParam(UserInfo requester, Object id);

    boolean checkReturn(UserInfo requester, Object ret);
}
