package org.javatraining.integration.gitlab;

import org.javatraining.integration.gitlab.api.GitlabAPI;
import org.javatraining.integration.gitlab.api.http.GitlabHTTPRequestor;
import org.javatraining.integration.gitlab.api.models.GitlabProject;
import org.javatraining.integration.gitlab.api.models.GitlabSession;
import org.javatraining.integration.gitlab.api.models.GitlabUser;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;

/**
 * Created by sergey on 26.05.15 at 16:22.
 */

public class GitlabIntegrator {
    private GitlabAPI api;

    private String rand = UUID.randomUUID().toString().replace("-", "").substring(0, 8);


    public boolean createConnect(String host, String login, String pass) throws IOException {
        GitlabSession s = GitlabAPI.connect(host, "root", "12345678");
        this.setApi(GitlabAPI.connect(host, s.getPrivateToken()));
        GitlabHTTPRequestor requestor = new GitlabHTTPRequestor(api);
        //requestor.method("GET");
        requestor.with("login", login).with("password", pass).to("session", GitlabUser.class);
        //FIXME dispatch didnt add login and pass to url's tail

        return true;
    }

    public URL getAPIUrl(String host) throws IOException {
        return new URL(host + "/api/v3/?private_token=" + getApi().getCurrentSession().getPrivateToken());
    }

    public URL getFullUrl() throws IOException {
        return getApi().getUrl("");
    }

    public boolean createRandomUser(String userName, String pass) throws IOException {
        GitlabUser gitUser = getApi().createUser(getRandomString("testEmail@gitlabapitest.com"),
                pass,
                userName,
                getRandomString("fullName"),
                getRandomString("skypeId"),
                getRandomString("linledin"),
                getRandomString("twitter"),
                "http://" + getRandomString("url.com"),
                10,
                getRandomString("externuid"),
                getRandomString("externprovidername"),
                getRandomString("bio"),
                false,
                false,
                false);

        return true;
    }

    public GitlabUser createAndGetSudoUserForSpecifiedUName(String userName) throws IOException {
        return getApi().getUserViaSudo(userName);
    }

    public GitlabProject createGitlabProject(String userName, String projectName) throws IOException {
        GitlabUser sudoUsr = createAndGetSudoUserForSpecifiedUName(userName);

        return api.createUserProject(sudoUsr.getId(), projectName);
    }

    private String getRandomString(String postfix) {
        return getRand() + "-" + postfix;
    }

    public GitlabAPI getApi() {
        return api;
    }

    public void setApi(GitlabAPI api) {
        this.api = api;
    }

    public String getRand() {
        return rand;
    }

    public void setRand(String rand) {
        this.rand = rand;
    }
}
