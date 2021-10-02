package com.depromeet.archive.security.vo;

import com.depromeet.archive.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User {

    private final String mailAddress;
    private long userId;
    private final UserRole userRole;
    private final String userName;
    private final Map<String, Object> attributes;
    private Collection<GrantedAuthority> authorities = new LinkedList<>();

    @Builder
    public UserPrincipal(String mailAddress, String userName, UserRole userRole, long userId, Map<String, Object> attributes) {
        this.mailAddress = mailAddress;
        this.userRole = userRole;
        this.attributes = attributes;
        this.userName = userName;
        this.userId = userId;
        authorities.add(new SimpleGrantedAuthority(userRole.toString()));
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return mailAddress;
    }
}
