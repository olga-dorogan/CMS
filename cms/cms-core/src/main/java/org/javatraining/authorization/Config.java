package org.javatraining.authorization;

/**
 * Created by olga on 29.05.15.
 */
public class Config {
    static final String REQUEST_HEADER_TOKEN = "x-session-token";

    static final String RESPONSE_HEADER_LOCATION = "location";
    static final String LOCATION_TEACHER = "/teacher";
    static final String LOCATION_STUDENT = "/student";

    private Config() {
    }
}
