package com.portfolio.scott.configs.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Override
    public Optional<String> getCurrentAuditor() {
        String userLogin = SecurityUtils.getCurrentUserLogin().orElse(ANONYMOUS_USER);
        if (userLogin.length() > 50) {
            userLogin = userLogin.substring(0, 50);
        }
        return Optional.of(userLogin);
    }

}
