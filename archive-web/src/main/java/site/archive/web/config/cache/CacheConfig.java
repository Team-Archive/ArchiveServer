package site.archive.web.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import site.archive.common.cache.CacheType;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new SimpleCacheManager();
        cacheManager.setCaches(caches());
        return cacheManager;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new CustomKeyGenerator();
    }

    private List<CaffeineCache> caches() {
        return Arrays.stream(CacheType.values())
                     .map(cache -> new CaffeineCache(cache.getKey(),
                                                     Caffeine.newBuilder()
                                                             .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                                                             .maximumSize(cache.getMaximumSize())
                                                             .recordStats()
                                                             .build()))
                     .toList();
    }

}
