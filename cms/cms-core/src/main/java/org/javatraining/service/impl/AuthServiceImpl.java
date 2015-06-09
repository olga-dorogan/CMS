package org.javatraining.service.impl;

import org.javatraining.config.AuthRole;
import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.service.AuthService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * Created by olga on 05.06.15.
 */
@ApplicationScoped
public class AuthServiceImpl implements AuthService {
    @Inject
    private GoogleUserinfoService googleUserinfoService;

    @Override
    public boolean isCredentialValid(@NotNull String idFromRequest, @NotNull String token) {
        String clientIdFromToken = googleUserinfoService.getClientIdByToken(token);
        // FIXME: get clientId from database by person id
        String clientIdFromDB = clientIdFromToken;
        return clientIdFromDB.equals(clientIdFromToken);
    }

    @Override
    public String getRoleById(@NotNull String idFromRequest) {
        //FIXME: get person role from database and return appropriate constant from AuthRole
        return AuthRole.TEACHER;
    }
}
