package org.savea.todoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
class AuditConfig {

  @Bean
  AuditorAware<String> auditor() {
      return () -> Optional.ofNullable(SecurityContextHolder.getContext()
                              .getAuthentication())
                           .filter(Authentication::isAuthenticated)
                           .map(Authentication::getName);
  }
}
