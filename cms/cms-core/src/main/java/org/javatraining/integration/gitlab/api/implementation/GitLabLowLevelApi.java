package org.javatraining.integration.gitlab.api.implementation;

import com.google.api.client.http.HttpResponse;
import org.javatraining.integration.gitlab.api.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.api.interfaces.GitLabAPI;
import org.javatraining.integration.gitlab.api.interfaces.GitLabRequestor;
import org.javatraining.integration.gitlab.api.model.GitLabProject;
import org.javatraining.integration.gitlab.api.model.GitLabProjectMember;
import org.javatraining.integration.gitlab.api.model.GitLabSession;
import org.javatraining.integration.gitlab.api.model.GitLabUser;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * The project name is cms.
 * Created by sergey on 03.06.15 at 9:39.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabLowLevelApi implements GitLabAPI, GitLabRequestor{
    private static final String API_NAMESPACE = "/api/v3";

    private String hostUrl;

    @Override
    public URL getApiUrl(Map<String, String> urlTail) throws MalformedURLException {
        String tail = "";
        for(Map.Entry<String, String> entry:urlTail.entrySet()){
            tail+=entry.getKey() + "=" + entry.getValue() + "&";
        }
        tail = tail.substring(0, tail.length()-2);//FIXME ili -2 ili -1
        return new URL(hostUrl + API_NAMESPACE + tail);
    }

    @Override
    public GitLabUser getUserByUserName(String userName) throws UserNotFoundException {
        return null;
    }

    @Override
    public boolean createNewGitLabUser(GitLabUser userProperties) {
        return false;
    }

    @Override
    public void updateUserByUserName(String userName) throws UserNotFoundException {

    }

    @Override
    public void removeUserByUserName(String userName) {

    }

    @Override
    public GitLabSession getGitlabSessionForSpecifiedUser(String userName, String email, String password) throws UserNotFoundException {
        return null;
    }

    @Override
    public boolean createNewProjectForUser(GitLabUser user) throws UserNotFoundException {
        return false;
    }

    @Override
    public boolean addProjectTeamMember(GitLabProjectMember projectMemberToAdd, GitLabProject project) throws UserNotFoundException {
        return false;
    }

    @Override
    public void removeProjectTeamMember(GitLabProject project, GitLabProjectMember projectMemberToRemove) throws UserNotFoundException {

    }

    @Override
    public HttpResponse execute(String method, URL url) {
        //HttpURLConnection connection = new Htt
        return null;
    }
}
