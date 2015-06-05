package org.javatraining.authorization.roles;

import org.javatraining.service.authorization.AuthorizationService;

/**
 * Created by olga on 29.05.15.
 */
//@WebFilter("/resources/teacher/*")
public class AuthorizationTeacherRoleFilter extends AbstractAuthorizationRoleFilter {
    public AuthorizationTeacherRoleFilter() {
        super(AuthorizationService.Role.TEACHER);
    }
}
