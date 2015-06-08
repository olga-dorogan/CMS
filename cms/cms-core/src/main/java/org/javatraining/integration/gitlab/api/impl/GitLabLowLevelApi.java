package org.javatraining.integration.gitlab.api.impl;

import flexjson.JSONDeserializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.javatraining.integration.gitlab.api.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.api.interfaces.GitLabAPI;
import org.javatraining.integration.gitlab.api.model.GitLabProject;
import org.javatraining.integration.gitlab.api.model.GitLabProjectMember;
import org.javatraining.integration.gitlab.api.model.GitLabSession;
import org.javatraining.integration.gitlab.api.model.GitLabUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The project name is cms.
 * Created by sergey on 03.06.15 at 9:39.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabLowLevelApi implements GitLabAPI {
    private static final String API_NAMESPACE = "/api/v3";
    private String hostUrl;
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String rootLogin;
    private String rootPass;
    private String rootEmail;

    public GitLabLowLevelApi(String hostUrl, String rootEmail, String rootLogin, String rootPass) {
        this.hostUrl = hostUrl;
        this.rootEmail = rootEmail;
        this.rootLogin = rootLogin;
        this.rootPass = rootPass;
    }

    @Override
    public URL getApiUrl(Map<String, String> urlTail, String methodData) throws MalformedURLException {
        String tail = (methodData.compareTo("") != 0) ? "/" + methodData + "/" : "";
        for (Map.Entry<String, String> entry : urlTail.entrySet()) {
            tail += entry.getKey() + "=" + entry.getValue() + "&";
        }
        tail = tail.substring(0, tail.length() - 2);
        return new URL(hostUrl + API_NAMESPACE + tail);
    }

    @Override
    public Collection<GitLabUser> getAllUsersFromGitlab() {
        try {
            GitLabSession session = getGitlabSessionForSpecifiedUser(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivate_token());
                    put("sudo", "root");
                }
            };
            HttpGet get = new HttpGet(getApiUrl(pairToTail, "users").toString());//FIXME change users to MethodData enum value
            HttpResponse response = httpClient.execute(get);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                JSONDeserializer<Collection<GitLabUser>> deserializer = new JSONDeserializer<>();
                return deserializer.deserialize(reader);//FIXME VOT TUT NE YASNO, NADEYUS' VERNET, SLEDIT' SUDA
            }
        } catch (UserNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GitLabUser getUserByUserName(String userName) throws UserNotFoundException {
        try {
            GitLabSession session = getGitlabSessionForSpecifiedUser(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("search", userName);
                    put("private_token", session.getPrivate_token());
                    put("sudo", "root");
                }
            };
            HttpGet get = new HttpGet(getApiUrl(pairToTail, "users").toString());//FIXME change users to MethodData enum value
            HttpResponse response = httpClient.execute(get);

            //FIXME vot tut null ne yasno
            //FIXME 2 http://www.mkyong.com/java/how-to-get-http-response-header-in-java/ bral otsuda schemu
            if (response.getFirstHeader("null").getValue().contains("200")) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    JSONDeserializer<GitLabUser> deserializer = new JSONDeserializer<>();
                }
            } else throw new UserNotFoundException("User with userName=" + userName + " not found in system");
        } catch (UserNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createGitLabUser(GitLabUser userProperties) {
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
        Map<String, String> pairToTail = new HashMap<String, String>() {
            {
                put("sudo", "root");
            }
        };
        try {
            HttpPost post = new HttpPost(getApiUrl(pairToTail, "").toString());
            StringEntity sEntity;
            sEntity = new StringEntity("\"username\":" + userName +
                    ",\"email\":" + email + ",\"password\":" + password, ContentType.APPLICATION_JSON);
            post.setEntity(sEntity);

            HttpResponse response = httpClient.execute(post);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                JSONDeserializer<GitLabSession> deserializer = new JSONDeserializer<>();
                return deserializer.deserialize(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
