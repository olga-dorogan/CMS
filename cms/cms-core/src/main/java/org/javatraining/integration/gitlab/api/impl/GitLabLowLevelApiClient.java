/*
package org.javatraining.integration.gitlab.api.impl;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.javatraining.integration.gitlab.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.exception.UserRequiredPropertiesIsNotComparable;
import org.javatraining.integration.gitlab.api.ifaces.GitLabAPIClient;
import org.javatraining.integration.gitlab.api.model.GitLabProjectEntity;
import org.javatraining.integration.gitlab.api.model.GitLabProjectMemberEntity;
import org.javatraining.integration.gitlab.api.model.GitLabSessionEntity;
import org.javatraining.integration.gitlab.api.model.GitLabUserEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * The project name is cms.
 * Created by sergey on 03.06.15 at 9:39.
 * For more information you should send mail to codedealerb@gmail.com
 *//*

public class GitLabLowLevelApiClient implements GitLabAPIClient {
    private static final String API_NAMESPACE = "/api/v3";
    private String hostUrl;
    private HttpClient httpClient = HttpClientBuilder.create().build();
    private String rootLogin;
    private String rootPass;
    private String rootEmail;

    public GitLabLowLevelApiClient(String hostUrl, String rootEmail, String rootLogin, String rootPass) {
        this.hostUrl = hostUrl;
        this.rootEmail = rootEmail;
        this.rootLogin = rootLogin;
        this.rootPass = rootPass;
    }

    private URL getApiUrl(Map<String, String> urlTail, String methodData) throws MalformedURLException {
        String tail = (methodData.compareTo("") != 0) ? "/" + methodData + "/" : "";
        for (Map.Entry<String, String> entry : urlTail.entrySet()) {
            tail += entry.getKey() + "=" + entry.getValue() + "&";
        }
        tail = tail.substring(0, tail.length() - 2);
        return new URL(hostUrl + API_NAMESPACE + tail);
    }

    @Override
    public Collection<GitLabUserEntity> getAllUsers() {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpGet get = new HttpGet(getApiUrl(pairToTail, MethodData.FOR_USERS.value).toString());
            HttpResponse response = httpClient.execute(get);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                JSONDeserializer<Collection<GitLabUserEntity>> deserializer = new JSONDeserializer<>();
                return deserializer.deserialize(reader);//FIXME VOT TUT NE YASNO, NADEYUS' VERNET, SLEDIT' SUDA
            }
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public GitLabUserEntity getUser(String userName) throws ResourceNotFoundException {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("search", userName);
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpGet get = new HttpGet(getApiUrl(pairToTail, MethodData.FOR_USERS.value).toString());
            HttpResponse response = httpClient.execute(get);
            //FIXME vot tut null ne yasno
            //FIXME 2 http://www.mkyong.com/java/how-to-get-http-response-header-in-java/ bral otsuda schemu
            if (response.getFirstHeader("null").getValue().contains("200")) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                    JSONDeserializer<GitLabUserEntity> deserializer = new JSONDeserializer<>();
                }
            } else throw new ResourceNotFoundException("User with userName=" + userName + " not found in system");
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createUser(GitLabUserEntity userProperties) {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpPost post = new HttpPost(getApiUrl(pairToTail, MethodData.FOR_USERS.value).toString());
            JSONSerializer serializer = new JSONSerializer();
            StringEntity sEntity = new StringEntity(serializer.serialize(userProperties), ContentType.APPLICATION_JSON);
            post.setEntity(sEntity);
            HttpResponse response = httpClient.execute(post);
            return response.getFirstHeader("null").getValue().contains("201");//FIXME dont forget to look at null header
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void updateUser(GitLabUserEntity userProperties) throws ResourceNotFoundException, UserRequiredPropertiesIsNotComparable {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            GitLabUserEntity gitLabUserEntity = getUser(userProperties.getUsername());
            if (gitLabUserEntity == null) throw new ResourceNotFoundException("User with userName="
                    + userProperties.getUsername() + " not found in system");
            if (!gitLabUserEntity.getEmail().equals(userProperties.getEmail()))
                throw new UserRequiredPropertiesIsNotComparable();
            HttpPut put = new HttpPut(getApiUrl(pairToTail, MethodData.FOR_USERS.value).toString());
            JSONSerializer serializer = new JSONSerializer();
            StringEntity sEntity = new StringEntity(serializer.serialize(userProperties), ContentType.APPLICATION_JSON);
            put.setEntity(sEntity);
            //HttpResponse response = httpClient.execute(put);
            httpClient.execute(put);
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUser(String userName) {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            GitLabUserEntity user = getUser(userName);
            HttpDelete put = new HttpDelete(getApiUrl(pairToTail, (MethodData.FOR_USERS.value + "/" + user.getId())).toString());
            httpClient.execute(put);
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public GitLabSessionEntity getSession(String userName, String email, String password) throws ResourceNotFoundException {
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
            response.getStatusLine().getStatusCode();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                JSONDeserializer<GitLabSessionEntity> deserializer = new JSONDeserializer<>();
                return deserializer.deserialize(reader);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean createProject(GitLabUserEntity user, GitLabProjectEntity projectProperties) throws ResourceNotFoundException {
        GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
        Map<String, String> pairToTail = new HashMap<String, String>() {
            {
                put("private_token", session.getPrivateToken());
                put("sudo", "root");
            }
        };
        try {
            getUser(user.getUsername());//except userNotFoundExc, if not exist
            HttpPost post = new HttpPost(getApiUrl(pairToTail, MethodData.FOR_PROJECTS.value + "/user/" + user.getId()).toString());
            StringEntity stringEntity = new StringEntity(
                    new JSONSerializer().serialize(projectProperties),
                    ContentType.APPLICATION_JSON);
            post.setEntity(stringEntity);

            HttpResponse response = httpClient.execute(post);
            return response.getFirstHeader("null").getValue().contains("201");
        } catch (MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Collection<GitLabProjectEntity> getAllProjects() {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpGet get = new HttpGet(getApiUrl(pairToTail, MethodData.FOR_PROJECTS.value + "/all").toString());
            HttpResponse response = httpClient.execute(get);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
                JSONDeserializer<Collection<GitLabProjectEntity>> deserializer = new JSONDeserializer<>();
                //MB NEED ADD TRANSFORMER FOR DATE
                return deserializer.deserialize(reader);
            }
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addProjectTeamMember(GitLabProjectMemberEntity projectMemberToAdd, GitLabProjectEntity project) throws ResourceNotFoundException {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpPost post = new HttpPost(getApiUrl(pairToTail, MethodData.FOR_PROJECTS.value + "/" + project.getId() + "/members").toString());
            StringEntity entity = new StringEntity(new JSONSerializer().serialize(projectMemberToAdd));

            HttpResponse response = httpClient.execute(post);
            return response.getFirstHeader("null").getValue().contains("201");
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void removeProjectTeamMember(GitLabProjectEntity project, GitLabProjectMemberEntity projectMemberToRemove) throws ResourceNotFoundException {
        try {
            GitLabSessionEntity session = getSession(rootLogin, rootEmail, rootPass);
            Map<String, String> pairToTail = new HashMap<String, String>() {
                {
                    put("private_token", session.getPrivateToken());
                    put("sudo", "root");
                }
            };
            HttpDelete delete = new HttpDelete(getApiUrl(pairToTail, MethodData.FOR_PROJECTS.value + "/" + project.getId() + "/members/" + projectMemberToRemove.getId()).toString());
            HttpResponse response = httpClient.execute(delete);
        } catch (ResourceNotFoundException | MalformedURLException | ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/
