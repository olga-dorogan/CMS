package org.javatraining.authorization.roles;

<<<<<<< HEAD:cms/cms-core/src/main/java/org/javatraining/authorization/roles/AuthorizationBaseRoleFilter.java
import org.javatraining.authorization.BaseFilter;
=======
import org.javatraining.service.authorization.AuthorizationService;
>>>>>>> 0de4b8e84b15f61b0789b3b4301ceb25fcd5cb30:cms/cms-core/src/main/java/org/javatraining/filter/RoleBasedAuthorizationFilter.java
import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.jboss.logging.Logger;

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
public abstract class AuthorizationBaseRoleFilter extends BaseFilter {
    private static final Logger log = Logger.getLogger(AuthorizationBaseRoleFilter.class);
    @EJB
    private AuthorizationService authorizationService;
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
    }

    public void doFilterOnRole(AuthorizationService.Role role, ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debugv("token: {0}, role: {1}", token, role);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            if (authorizationService.getRoleByClientId(googleUserinfoService.getClientIbByToken(token)) != role) {
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
