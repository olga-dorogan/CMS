package org.javatraining.service.impl;

import org.javatraining.auth.AuthRole;
import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.service.AuthService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

/**
 * Created by olga on 05.06.15.
 */
@Stateless
public class AuthServiceImpl implements AuthService {
    @EJB
    private GoogleUserinfoService googleUserinfoService;

    @Override
    public boolean isClientIdsFromDBAndFromTokenEqual(@NotNull String idFromRequest, @NotNull String token) {
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
