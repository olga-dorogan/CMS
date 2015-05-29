package org.javatraining.filter;

import org.javatraining.integration.google.oauth.TokenVerifierService;
import org.javatraining.integration.google.oauth.exception.AuthException;
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
 * Created by olga on 28.05.15.
 */
@WebFilter("/resources/*")
public class AuthenticationFilter extends BaseFilter {
    private static final Logger log = Logger.getLogger(AuthenticationFilter.class);
    @EJB
    private TokenVerifierService tokenVerifierService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        log.debugv("token: {0}", token);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            if (token == null || !tokenVerifierService.verifyToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } catch (AuthException exception) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        chain.doFilter(request, response);
    }
}
