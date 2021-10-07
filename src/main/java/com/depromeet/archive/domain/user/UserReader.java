package com.depromeet.archive.domain.user;

import com.depromeet.archive.domain.user.entity.User;

public interface UserReader {
    User findUserByMail(String mailAddress);
    User findUserById(long userId);
}
