package org.javatraining.integration.gitlab.api.impl;

import org.javatraining.entity.PersonRole;
import org.javatraining.integration.gitlab.impl.GitLabService;
import org.javatraining.model.PersonVO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * The project name is cms.
 * Created by sergey on 12.06.15 at 21:38.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabServiceTest {
    private GitLabService service;
    private PersonVO testPerson;

//    @Ignore
    @Before
    public void setUpGitlabClient() {
        service = new GitLabService("http://localhost", "root", "12345678", "admin@example.com");
        testPerson = new PersonVO(new Long(1), "test1", "test1", "test1@mail.ru", PersonRole.STUDENT);
    }

//    @Ignore
    @After
    public void closeClient() {
        service = null;
    }

//    @Ignore
    @Test
    public void testGetResultOfCreatingNewUser() {
        assertTrue(service.addPerson(testPerson));
    }
}
