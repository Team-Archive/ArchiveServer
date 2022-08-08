package site.archive.web.api.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import site.archive.domain.user.UserInfo;
import site.archive.web.api.resolver.annotation.RequestUser;
import site.archive.web.config.security.util.SecurityUtils;

@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameter().getType() == UserInfo.class && methodParameter.hasParameterAnnotation(RequestUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) {
        return SecurityUtils.getCurrentUserInfo();
    }
}
