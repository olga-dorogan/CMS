package org.javatraining.authorization.roles;

import org.javatraining.service.authorization.AuthorizationService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by olga on 29.05.15.
 */
@WebFilter("/resources/teacher/*")
public class AuthorizationTeacherRoleFilter extends AuthorizationBaseRoleFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilter(request, response, chain);
        super.doFilterOnRole(AuthorizationService.Role.TEACHER, request, response, chain);
    }
}
