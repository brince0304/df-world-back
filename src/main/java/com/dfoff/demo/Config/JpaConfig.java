package com.dfoff.demo.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
@Slf4j
public class JpaConfig {
    @Bean
    public AuditorAware<String> auditorAware() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(authentication!=null) {
                log.info("authentication: {}", authentication);
                return () -> Optional.of(authentication.getName());
        }
        else{
            log.info(SecurityContextHolder.getContext().toString());
            return () -> Optional.of("anonymous");
        }
    }
}
