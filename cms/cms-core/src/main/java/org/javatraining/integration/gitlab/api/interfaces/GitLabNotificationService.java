package org.javatraining.integration.gitlab.api.interfaces;

import org.javatraining.integration.gitlab.api.model.GitLabUser;

/**
 * The project name is cms.
 * Created by sergey on 17.06.15 at 12:38.
 * For more information you should send mail to codedealerb@gmail.com
 */
public interface GitLabNotificationService {

    public void sendUserProperties(String from, GitLabUser user);
}
