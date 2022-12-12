package site.archive.web.config.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    private List<CaffeineCache> caches() {
        return Arrays.stream(CacheType.values())
                     .map(cache -> new CaffeineCache(cache.getName(),
                                                     Caffeine.newBuilder()
                                                             .expireAfterWrite(cache.getExpireAfterWrite(), TimeUnit.SECONDS)
                                                             .maximumSize(1)
                                                             .recordStats()
                                                             .build()))
                     .toList();
    }

}
