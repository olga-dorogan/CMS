package org.javatraining.filter.role;

import org.javatraining.filter.RoleBasedAuthorizationFilter;
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
@WebFilter("/resources/student/*")
public class StudentAuthorizationFilter extends RoleBasedAuthorizationFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        super.doFilterOnRole(AuthorizationService.Role.STUDENT, request, response, chain);
    }
}
