package org.javatraining.auth;

/**
 * Created by olga on 05.06.15.
 */
public abstract class Config {
    public static final String REQUEST_HEADER_TOKEN = "x-session-token";
    public static final String REQUEST_HEADER_ID = "x-session-id";

    private Config() {
    }
}
