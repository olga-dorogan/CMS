package org.javatraining.service.authorization.impl;

import org.javatraining.integration.google.oauth.impl.GoogleUserinfoServiceImpl;
import org.javatraining.service.authorization.AuthorizationService;

import javax.ejb.Stateless;

/**
 * Created by olga on 29.05.15.
 */
@Stateless
public class MockAuthorizationServiceImpl implements AuthorizationService {

    @Override
    public Role getRoleByClientId(String clientId) {
//        return (Math.random() > 0.5) ? Role.TEACHER : Role.STUDENT;
        return Role.STUDENT;
    }

    @Override
    public void registerUser(GoogleUserinfoServiceImpl.Userinfo userinfo) {
    }

}
