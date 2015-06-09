package org.javatraining.authorization.roles;

import org.javatraining.service.authorization.AuthorizationService;

/**
 * Created by olga on 29.05.15.
 */
//@WebFilter("/resources/student/*")
public class AuthorizationStudentRoleFilter extends AbstractAuthorizationRoleFilter {
    protected AuthorizationStudentRoleFilter() {
        super(AuthorizationService.Role.STUDENT);
    }
}