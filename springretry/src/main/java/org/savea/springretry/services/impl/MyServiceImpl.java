package org.savea.springretry.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.savea.springretry.services.MyService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@Slf4j
public class MyServiceImpl implements MyService {

    @Override
    public void retryService() {
        log.info("throw RuntimeException in method retryService()");
        throw new RuntimeException();
    }

    @Override
    public void retryServiceWithRecovery(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            log.info("throw SQLException in method retryServiceWithRecovery()");
            throw new SQLException();
        }
    }

    @Override
    public void retryServiceWithCustomization(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            log.info("throw SQLException in method retryServiceWithCustomization()");
            throw new SQLException();
        }
    }

    @Override
    public void retryServiceWithExternalConfiguration(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            log.info("throw SQLException in method retryServiceWithExternalConfiguration()");
            throw new SQLException();
        }
    }

    @Override
    public void recover(SQLException e, String sql) {
        log.info("In recover method");
    }

    @Override
    public void templateRetryService() {
        log.info("throw RuntimeException in method templateRetryService()");
        throw new RuntimeException();
    }
}
