package org.javatraining.integration.gitlab.impl;

import org.javatraining.integration.gitlab.api.ifaces.GitLabAPIClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 * The project name is cms.
 * Created by sergey on 09.06.15 at 16:03.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabService {

    private GitLabAPIClient gitLabClient;

    public GitLabService(String host) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(host);

        this.gitLabClient = target.proxy(GitLabAPIClient.class);
    }
    


}
