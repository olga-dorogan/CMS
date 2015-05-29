package org.javatraining.filter;

import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.javatraining.integration.google.oauth.exception.GoogleConnectionAuthException;
import org.javatraining.service.authorization.AuthorizationService;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 29.05.15.
 */
@WebFilter("/resources/auth")
public class InitAuthorizationFilter extends BaseFilter {
    private static final Logger log = Logger.getLogger(InitAuthorizationFilter.class);
    @EJB
    private AuthorizationService authorizationService;
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        log.debugv("token: {0}", token);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            String clientId = googleUserinfoService.getClientIbByToken(token);
            AuthorizationService.Role role = authorizationService.getRoleByClientId(clientId);
            if (role == AuthorizationService.Role.UNAUTHORIZED) {
                authorizationService.registerUser(googleUserinfoService.getUserInfoByToken(token));
                role = authorizationService.getRoleByClientId(clientId);
            }
            switch (role) {
                case TEACHER:
                    httpResponse.setHeader(Config.RESPONSE_HEADER_LOCATION, Config.LOCATION_TEACHER);
                    break;
                case STUDENT:
                    httpResponse.setHeader(Config.RESPONSE_HEADER_LOCATION, Config.LOCATION_STUDENT);
                    break;
                default:
                    throw new AuthException("Role of user with id = " + clientId + " is undefined");
            }
            httpResponse.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        } catch (GoogleConnectionAuthException e) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (AuthException e) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}
