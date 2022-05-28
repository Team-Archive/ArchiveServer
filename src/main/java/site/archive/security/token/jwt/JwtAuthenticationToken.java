package site.archive.security.token.jwt;

import site.archive.domain.user.info.UserInfo;
import site.archive.security.common.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;

public class JwtAuthenticationToken implements Authentication {

    private final Collection<GrantedAuthority> authorities = new LinkedList<>();
    private final UserPrincipal principal;
    private final UserInfo userInfo;
    private final UserDetails details;
    private boolean authenticated = true;

    public JwtAuthenticationToken(UserInfo token) {
        authorities.add(new SimpleGrantedAuthority(token.getUserRole().toString()));
        principal = UserPrincipal
            .builder()
            .userInfo(token)
            .build();
        details = new User(token.getMailAddress(), "", authorities);
        userInfo = token;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return details;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        this.authenticated = b;
    }

    @Override
    public String getName() {
        return principal.getName();
    }

}
