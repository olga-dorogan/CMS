package org.javatraining.authorization;

import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.TokenVerifierService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 28.05.15.
 */
@WebFilter("/resources/*")
public class AuthenticationFilter extends AbstractFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationFilter.class);
    @EJB
    private TokenVerifierService tokenVerifierService;
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        log.trace("token: {}", token);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            // verify token
            if (token == null || !tokenVerifierService.verifyToken(token)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // if the client_id is the part of the request, verify it
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()); // /resources/student | teacher/{clientId}/smth
            String[] pathParts = path.split("/");
            if (pathParts.length >= 4) {
                String clientIdFromToken = googleUserinfoService.getClientIdByToken(token);
                if (!pathParts[3].equals(clientIdFromToken)) {
                    log.trace("Authentication fail: clientIdFromRequest = {}; clientIdFromToken = {}", pathParts[3], clientIdFromToken);
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
        } catch (AuthException exception) {
            httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        chain.doFilter(request, response);
    }
}
