package site.archive.web.config.security.authz.annotation;

import site.archive.web.config.security.authz.permissionhandler.PermissionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {

    Class<? extends PermissionHandler> handler();

    String id() default "";

}
