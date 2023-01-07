package su.sa1zer.bookparser.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadConfig extends AsyncConfigurerSupport {

    @Value("${parsers.thread-pools}")
    private int threadCount;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(threadCount);
        threadPoolTaskExecutor.setMaxPoolSize(threadCount);
        threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }
}
