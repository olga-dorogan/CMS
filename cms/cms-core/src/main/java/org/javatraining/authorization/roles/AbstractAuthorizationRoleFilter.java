package org.javatraining.authorization.roles;

import org.javatraining.authorization.AbstractFilter;
import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.javatraining.service.authorization.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 29.05.15.
 */
public abstract class AbstractAuthorizationRoleFilter extends AbstractFilter {
    private static final Logger log = LoggerFactory.getLogger(AbstractAuthorizationRoleFilter.class);
    @EJB
    private AuthorizationService authorizationService;
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    private final AuthorizationService.Role expectedRole;

    protected AbstractAuthorizationRoleFilter(AuthorizationService.Role expectedRole) {
        this.expectedRole = expectedRole;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        log.trace("token: {}, role: {}", token, expectedRole);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            if (authorizationService.getRoleByClientId(googleUserinfoService.getClientIdByToken(token)) != expectedRole) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (AuthException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, response);
    }
}