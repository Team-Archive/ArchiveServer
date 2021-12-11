package com.depromeet.archive.security.authorization;

import com.depromeet.archive.domain.user.info.UserInfo;
import com.depromeet.archive.security.authorization.annotation.RequirePermission;
import com.depromeet.archive.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ArchivePermissionAdvice {

    private final ApplicationContext context;

    @Around("@annotation(com.depromeet.archive.security.authorization.annotation.RequirePermission)")
    public Object handlePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequirePermission annotation = signature.getMethod().getAnnotation(RequirePermission.class);
        ArchivePermissionHandler permissionHandler = context.getBean(annotation.handler());
        UserInfo requester = SecurityUtils.getCurrentUserInfo();

        Object idParam = getParamObjByName(joinPoint.getArgs(), signature, annotation.id());
        if (idParam != null && !permissionHandler.checkParam(requester, idParam))
            throw new AccessDeniedException("리소스에 접근 권한이 없습니다");

        Object returnVal = joinPoint.proceed();

        if (!permissionHandler.checkReturn(requester, returnVal))
            throw new AccessDeniedException("리소스에 접근 권한이 없습니다.");

        return returnVal;
    }


    private Object getParamObjByName(Object[] args, MethodSignature signature, String paramName) {
        String[] paramNames = signature.getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            String nameToCheck = paramNames[i];
            if (nameToCheck.equals(paramName))
                return args[i];
        }
        return null;
    }
}
