package org.javatraining.integration.google.oauth;

import org.javatraining.integration.google.oauth.impl.GoogleUserinfoServiceImpl;

/**
 * Created by olga on 29.05.15.
 */
public interface GoogleUserinfoService {
    String getClientIdByToken(String token);

    GoogleUserinfoServiceImpl.Userinfo getUserInfoByToken(String token);
}
