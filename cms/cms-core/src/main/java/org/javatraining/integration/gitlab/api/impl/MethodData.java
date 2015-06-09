package org.javatraining.integration.gitlab.api.impl;

/**
 * The project name is cms.
 * Created by sergey on 08.06.15 at 21:42.
 * For more information you should send mail to codedealerb@gmail.com
 */
public enum MethodData {
    FOR_USERS("users"),
    FOR_PROJECTS("projects");


    public final String value;

    MethodData(String value) {
        this.value = value;
    }

}
