package org.savea.springretry.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@EnableRetry
@PropertySource("classpath:retryConfig.properties")
@ComponentScan(basePackages = "org.savea.springretry")
public class AppConfig {

    /**
     * The RetryPolicy determines when an operation should be retried.
     *<p/>
     * A SimpleRetryPolicy is used to retry a fixed number of times.
     * On the other hand, the BackOffPolicy is used to control backoff between retry attempts.
     *<p/>
     * Finally, a FixedBackOffPolicy pauses for a fixed period of time before continuing.
     * @return RetryTemplate
     */
    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();

        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(2000l);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(2);
        retryTemplate.setRetryPolicy(retryPolicy);

        // Registering our listener (DefaultListenerSupport) to our RetryTemplate bean
        retryTemplate.registerListener(new DefaultListenerSupport());
        return retryTemplate;
    }
}