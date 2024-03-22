package org.savea.springretry.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.listener.MethodInvocationRetryListenerSupport;

/**
 * Listeners provide additional callbacks upon retries.
 * And we can use these for various cross-cutting concerns across different retries.
 * <p/>
 * The callbacks are provided in a RetryListener interface.
 * <p/>
 * The open and close callbacks come before and after the entire retry,
 * while onError applies to the individual RetryCallback calls.
 */
@Slf4j
public class DefaultListenerSupport extends MethodInvocationRetryListenerSupport {

    @Override
    public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("onClose");
        super.close(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
        log.info("onError");
        super.onError(context, callback, throwable);
    }

    @Override
    public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
        log.info("onOpen");
        return super.open(context, callback);
    }

}
