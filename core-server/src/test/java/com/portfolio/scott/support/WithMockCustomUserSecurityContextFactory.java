package com.portfolio.scott.support;

import com.portfolio.scott.configs.security.TokenUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {

    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(customUser.role().toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        TokenUser userDetail = new TokenUser(customUser.loginId(), customUser.password(), authorities);
        userDetail.setUserId(customUser.userId());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, "", authorities);
        authentication.setDetails(userDetail);

        context.setAuthentication(authentication);
        return context;
    }

}
