package site.archive.config.security.authz;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import site.archive.config.security.authz.annotation.RequirePermission;
import site.archive.config.security.util.SecurityUtils;

@Aspect
@Component
@RequiredArgsConstructor
public class ArchivePermissionAdvice {

    private final ApplicationContext context;

    @Around("@annotation(site.archive.config.security.authz.annotation.RequirePermission)")
    public Object handlePermission(ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = (MethodSignature) joinPoint.getSignature();
        var annotation = signature.getMethod().getAnnotation(RequirePermission.class);
        var permissionHandler = context.getBean(annotation.handler());
        var requester = SecurityUtils.getCurrentUserInfo();

        var idParam = getParamObjByName(joinPoint.getArgs(), signature, annotation.id());
        if (idParam != null && !permissionHandler.checkParam(requester, idParam)) {
            throw new AccessDeniedException("리소스에 접근 권한이 없습니다");
        }

        return joinPoint.proceed();
    }

    private Object getParamObjByName(Object[] args, MethodSignature signature, String paramName) {
        var paramNames = signature.getParameterNames();
        for (int i = 0; i < paramNames.length; i++) {
            var nameToCheck = paramNames[i];
            if (nameToCheck.equals(paramName)) {
                return args[i];
            }
        }
        return null;
    }
}
