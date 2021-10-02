package com.depromeet.archive.domain.user;


import com.depromeet.archive.domain.user.entity.User;

public interface UserStore {
    void saveUser(User user);
    void removeUser(User user);
}
