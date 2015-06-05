package org.javatraining.auth;

import org.javatraining.integration.google.oauth.TokenVerifierService;
import org.javatraining.service.AuthService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * Created by olga on 05.06.15.
 */
@Auth(roles = {})
@Interceptor
public class AuthInterceptor implements Serializable {
    @Inject
    private HttpServletRequest request;
    @EJB
    private TokenVerifierService tokenVerifierService;
    @EJB
    private AuthService authService;

    @AroundInvoke
    public Object invoke(final InvocationContext context) throws Exception {
        String[] methodAllowedRoles = getRoles(context.getMethod());
        // if the method wasn't annotated or role field in annotation is empty,
        // process request
        if (methodAllowedRoles == null || methodAllowedRoles.length == 0) {
            return context.proceed();
        }
        String token = getToken();
        // if the token wasn't set in request header or if it's not valid,
        // return response with code 401
        if (token == null || !tokenVerifierService.isTokenValid(token)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String id = getId();
        // if the id wasn't set in request header or if clientIDs from database and from the google are not equal,
        // return response with code 401
        if (id == null || !authService.isClientIdsFromDBAndFromTokenEqual(id, token)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String userRole = authService.getRoleById(id);
        // if the user role is undefined or invoked method has another allowed roles,
        // return response with code 401
        if (userRole == null || !isUserRoleAllowedForMethod(userRole, methodAllowedRoles)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        // if all auth steps are passed,
        // process request
        return context.proceed();
    }

    private String[] getRoles(Method method) {
        if (method.isAnnotationPresent(Auth.class)) {
            return method.getAnnotation(Auth.class).roles();
        }

        if (method.getDeclaringClass().isAnnotationPresent(Auth.class)) {
            return method.getDeclaringClass().getAnnotation(Auth.class).roles();
        }
        return null;
    }

    private String getToken() {
        return request.getHeader(Config.REQUEST_HEADER_TOKEN);
    }

    private String getId() {
        return request.getHeader(Config.REQUEST_HEADER_ID);
    }

    private boolean isUserRoleAllowedForMethod(String role, String[] allowedRoles) {
        for (int i = 0; i < allowedRoles.length; i++) {
            if (role.equals(allowedRoles[i])) {
                return true;
            }
        }
        return false;
    }

}
