package site.archive.web.config.cache;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;

import java.lang.reflect.Method;

// Cacheable 적용범위를 method 이름 단위로 세분화 하기 위함
public class CustomKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return method.getName() + SimpleKeyGenerator.generateKey(params);
    }

}
