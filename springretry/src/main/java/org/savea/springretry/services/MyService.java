package org.savea.springretry.services;

import java.sql.SQLException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;


public interface MyService {

    /**
     * @Retryable Without Recovery
     * <p/>
     * Since we have not specified any exceptions here, retry will be attempted for all the exceptions.
     * Also, once the max attempts are reached and there is still an exception, ExhaustedRetryException will be thrown.
     *<p/>
     * Per @Retryable's default behavior, the retry may happen up to three times, with a delay of one second between retries.
     */
    @Retryable
    void retryService();

    /**
     * @Retryable and @Recover
     * <p/>
     * Here, the retry is attempted when an SQLException is thrown.
     * The @Recover annotation defines a separate recovery method when a @Retryable method fails with a specified exception.
     * <p/>
     * Consequently, if the retryServiceWithRecovery method keeps throwing an SqlException after three attempts,
     * the recover() method will be called.
     * <p/>
     * The recovery handler should have the first parameter of type Throwable (optional) and the same return type.
     * The following arguments are populated from the argument list of the failed method in the same order.
     * @param sql, the SQL query
     * @throws SQLException if the SQL query is empty
     */
    @Retryable(retryFor = SQLException.class)
    void retryServiceWithRecovery(String sql) throws SQLException;

    /**
     * Customizing @Retryable’s Behavior
     * <p/>
     * There will be up to two attempts and a delay of 100 milliseconds.
     * @param sql, the SQL query
     * @throws SQLException if the SQL query is empty
     */
    @Retryable(retryFor = SQLException.class , maxAttempts = 2, backoff = @Backoff(delay = 100))
    void retryServiceWithCustomization(String sql) throws SQLException;

    /**
     * We can also use properties in the @Retryable annotation.
     * <p/>
     * Please note that we are now using maxAttemptsExpression and delayExpression instead of maxAttempts and delay.
     * @param sql, the SQL query
     * @throws SQLException if the SQL query is empty
     */
    @Retryable(retryFor = SQLException.class, maxAttemptsExpression = "${retry.maxAttempts}",
            backoff = @Backoff(delayExpression = "${retry.maxDelay}"))
    void retryServiceWithExternalConfiguration(String sql) throws SQLException;

    @Recover
    void recover(SQLException e, String sql);

    void templateRetryService();
}
