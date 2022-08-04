package site.archive.config.security.authz.annotation;

import site.archive.config.security.authz.permissionhandler.ArchivePermissionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequirePermission {
    Class<? extends ArchivePermissionHandler> handler();

    String id() default "";
}
