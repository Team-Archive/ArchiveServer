package com.depromeet.archive.api.resolver;

import com.depromeet.archive.common.exception.BaseException;
import com.depromeet.archive.common.exception.ForbiddenActionException;
import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.token.jwt.JwtAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameter().getType() == UserInfo.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
       try {
           JwtAuthenticationToken token = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
           return token.getUserInfo();
       } catch (NullPointerException e) {
           throw new ForbiddenActionException("유저 인증 정보가 존재하지 않습니다");
       } catch (ClassCastException e) {
           throw new BaseException("잘못된 인증 토큰입니다. JwtAuthenticationToken 이 필요합니다.");
       }
    }
}
