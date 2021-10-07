package com.depromeet.archive.security.oauth;

import com.depromeet.archive.security.vo.UserPrincipal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserPrincipalConverter extends Converter<OAuth2User, UserPrincipal> {

    public String getProviderId();
}
