package com.savea;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.savea.springretry.configs.AppConfig;
import org.savea.springretry.services.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class SpringRetryIntegrationTest {

    @SpyBean
    private MyService myService;
    @Value("${retry.maxAttempts}")
    private String maxAttempts;

    @Autowired
    private RetryTemplate retryTemplate;

    /**
     * Expect to see the method retried three times in intervals of 1 second.
     * You should see three error logs in the console relating to the {@link RuntimeException}.
     */
    @Test(expected = RuntimeException.class)
    public void givenRetryService_whenCallWithException_thenRetry() {
        myService.retryService();
    }

    @Test
    public void givenRetryServiceWithRecovery_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithRecovery(null);
        verify(myService, times(3)).retryServiceWithRecovery(any());
    }

    @Test
    public void givenRetryServiceWithCustomization_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithCustomization(null);

        /*
         * This test method is used to verify the behavior of the `retryServiceWithCustomization` method in the `MyService` class.
         *
         * The `verify` method from Mockito is used to check that the `retryServiceWithCustomization` method of the `myService` object
         * was called a certain number of times, as specified by the `maxAttempts` value.
         *
         * The `times` method from Mockito is used with `Integer.parseInt(maxAttempts)`, which converts the `maxAttempts` string to an integer.
         *
         * The `any()` method from Mockito is used as an argument to the `retryServiceWithCustomization` method. It is a flexible argument matcher
         * that accepts any argument of the given type.
         */
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithCustomization(any());
    }

    @Test
    public void givenRetryServiceWithExternalConfiguration_whenCallWithException_thenRetryRecover() throws SQLException {
        myService.retryServiceWithExternalConfiguration(null);
        verify(myService, times(Integer.parseInt(maxAttempts))).retryServiceWithExternalConfiguration(any());
    }

    /**
     * Testing the use of the retry template.
     */
    @Test(expected = RuntimeException.class)
    public void givenTemplateRetryService_whenCallWithException_thenRetry() {
        retryTemplate.execute(arg0 -> {
            myService.templateRetryService();
            return null;
        });
    }
}