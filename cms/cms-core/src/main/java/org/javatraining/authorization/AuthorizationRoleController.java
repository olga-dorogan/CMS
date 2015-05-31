package org.javatraining.authorization;

import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.javatraining.integration.google.oauth.exception.GoogleConnectionAuthException;
import org.javatraining.service.authorization.AuthorizationService;
import org.jboss.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 29.05.15.
 */
@WebServlet("/resources/auth")
public class AuthorizationRoleController extends HttpServlet {
    private static final Logger log = Logger.getLogger(AuthorizationRoleController.class);
    @EJB
    private AuthorizationService authorizationService;
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getHeader(Config.REQUEST_HEADER_TOKEN);
        log.debugv("token: {0}", token);
        try {
            String clientId = googleUserinfoService.getClientIbByToken(token);
            AuthorizationService.Role role = authorizationService.getRoleByClientId(clientId);
            if (role == AuthorizationService.Role.UNAUTHORIZED) {
                authorizationService.registerUser(googleUserinfoService.getUserInfoByToken(token));
                role = authorizationService.getRoleByClientId(clientId);
            }
            switch (role) {
                case TEACHER:
                    resp.setHeader(Config.RESPONSE_HEADER_LOCATION, Config.LOCATION_TEACHER);
                    break;
                case STUDENT:
                    resp.setHeader(Config.RESPONSE_HEADER_LOCATION, Config.LOCATION_STUDENT);
                    break;
                default:
                    throw new AuthException("Role of user with id = " + clientId + " is undefined");
            }
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        } catch (GoogleConnectionAuthException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        } catch (AuthException e) {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
    }
}
