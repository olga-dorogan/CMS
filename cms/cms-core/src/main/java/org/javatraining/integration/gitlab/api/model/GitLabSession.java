package org.javatraining.integration.gitlab.api.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 16:44.
 * For more information you should send mail to codedealerb@gmail.com
 */

public class GitLabSession {
    //FIXME does it need username and email?
    @JsonProperty("private_token")
    private String privateToken;


    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }
}
