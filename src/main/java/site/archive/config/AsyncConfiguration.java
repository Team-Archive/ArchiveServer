package site.archive.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableConfigurationProperties(AsyncConfiguration.MailExecutorProperty.class)
@EnableAsync
@RequiredArgsConstructor
@Slf4j
public class AsyncConfiguration implements AsyncConfigurer {

    private final MailExecutorProperty mailExecutorProperty;

    @Override
    @Bean("mailExecutor")
    public Executor getAsyncExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(mailExecutorProperty.getCorePoolSize());
        executor.setMaxPoolSize(mailExecutorProperty.getMaxPoolSize());
        executor.setQueueCapacity(mailExecutorProperty.getQueueCapacity());
        executor.setAwaitTerminationSeconds(mailExecutorProperty.getAwaitTerminationSeconds());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setThreadNamePrefix(MailExecutorProperty.THREAD_NAME_PREFIX);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (exception, method, params) ->
                   log.error("Async Exception handler: '{}' threw unexpected exception ",
                             method.toGenericString(), exception);
    }

    @ConfigurationProperties(prefix = "spring.mail.executor")
    @ConstructorBinding
    @AllArgsConstructor
    @Getter
    @ToString
    public static class MailExecutorProperty {

        private static final String THREAD_NAME_PREFIX = "mail-executor-";

        private int corePoolSize;
        private int maxPoolSize;
        private int queueCapacity;
        private int awaitTerminationSeconds;

    }

}
