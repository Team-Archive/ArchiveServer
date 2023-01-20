package site.archive.config

import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import site.archive.config.AsyncConfiguration.MailExecutorProperty
import java.lang.reflect.Method
import java.util.concurrent.Executor

@Configuration
@EnableConfigurationProperties(MailExecutorProperty::class)
@EnableAsync
class AsyncConfiguration(val mailExecutorProperty: MailExecutorProperty) : AsyncConfigurer {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean("mailExecutor")
    override fun getAsyncExecutor(): Executor? {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = mailExecutorProperty.corePoolSize
        executor.maxPoolSize = mailExecutorProperty.maxPoolSize
        executor.queueCapacity = mailExecutorProperty.queueCapacity
        executor.setAwaitTerminationSeconds(mailExecutorProperty.awaitTerminationSeconds)
        executor.setWaitForTasksToCompleteOnShutdown(true)
        executor.setThreadNamePrefix(MailExecutorProperty.THREAD_NAME_PREFIX)
        executor.initialize()
        return executor
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler? {
        return AsyncUncaughtExceptionHandler { exception: Throwable?, method: Method, _: Array<Any?>? ->
            log.error("Async Exception handler: '{}' threw unexpected exception ", method.toGenericString(), exception)
        }
    }

    @ConfigurationProperties(prefix = "spring.mail.executor")
    class MailExecutorProperty(
        val corePoolSize: Int = 0,
        val maxPoolSize: Int = 0,
        val queueCapacity: Int = 0,
        val awaitTerminationSeconds: Int = 0
    ) {
        companion object {
            const val THREAD_NAME_PREFIX = "mail-executor-"
        }
    }

}