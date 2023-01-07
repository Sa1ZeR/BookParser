package su.sa1zer.bookparser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class ThreadConfig extends AsyncConfigurerSupport {

    public static final ExecutorService GLOBAL_SES = Executors.newSingleThreadExecutor();

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
