package org.javatraining.integration.gitlab.api.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * The project name is cms.
 * Created by sergey on 05.06.15 at 20:10.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabProjectMember {
    @JsonProperty("access_level")
    private int accessLevel;

    public GitLabAccessLevel getAccessLevel() {
        return GitLabAccessLevel.fromAccessValue(accessLevel);
    }

    public void setAccessLevel(GitLabAccessLevel accessLevel) {
        this.accessLevel = accessLevel.accessValue;
    }
}
