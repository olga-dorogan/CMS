package org.javatraining.integration.gitlab;

import org.javatraining.integration.gitlab.api.GitlabAPI;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * The project name is gitlabapidemo.
 * Created by sergey on 27.05.15 at 16:08.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitlabIntegratorTest {
    private GitlabIntegrator git = new GitlabIntegrator();
    public static final String GIT_HOST = "http://localhost:80/git.serhb.com";
    public static final String LOGIN = "root";
    public static final String PASSWORD = "12345678";

    @Test
    public void testGetConnectionFromGitLab() throws IOException {
        assertTrue(git.createConnect(GIT_HOST, LOGIN, PASSWORD));
        assertEquals(git.getApi().getClass(), GitlabAPI.class);
    }


}
