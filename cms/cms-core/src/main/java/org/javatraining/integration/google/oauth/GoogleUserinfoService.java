package org.javatraining.integration.google.oauth;

import org.javatraining.integration.google.oauth.impl.GoogleUserinfoServiceImpl;

/**
 * Created by olga on 29.05.15.
 */
public interface GoogleUserinfoService {
    String getClientIbByToken(String token);

    GoogleUserinfoServiceImpl.Userinfo getUserInfoByToken(String token);
}
