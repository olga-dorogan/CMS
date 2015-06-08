package org.javatraining.integration.gitlab.api.model;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 16:44.
 * For more information you should send mail to codedealerb@gmail.com
 */

public class GitLabSession {
    //@JsonProperty("private_token")
    //@QueryParam()?????
    private String private_token;//FIXME flexjson hasnt jsonproperty, need own transformator


    public String getPrivate_token() {
        return private_token;
    }

    public void setPrivate_token(String private_token) {
        this.private_token = private_token;
    }
}
