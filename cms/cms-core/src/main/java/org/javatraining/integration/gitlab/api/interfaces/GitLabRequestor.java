package org.javatraining.integration.gitlab.api.interfaces;

import com.google.api.client.http.HttpResponse;

import java.net.URL;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 16:35.
 * For more information you should send mail to codedealerb@gmail.com
 */
public interface GitLabRequestor {

    public HttpResponse execute(String method, URL url);

}
