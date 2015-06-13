package org.javatraining.integration.gitlab.impl;

import org.javatraining.integration.gitlab.api.ifaces.GitLabAPIClient;
import org.javatraining.integration.gitlab.api.model.*;
import org.javatraining.integration.gitlab.exception.ResourceNotFoundException;
import org.javatraining.integration.gitlab.exception.UserRequiredPropertiesIsNotComparable;
import org.javatraining.integration.gitlab.converter.PersonConverter;
import org.javatraining.model.PersonVO;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * The project name is cms.
 * Created by sergey on 09.06.15 at 16:03.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabService {
//    private static final String ROOT_LOGIN = "root";
//    private static final String ROOT_MAIL = "admin@example.com";
//    private static final String ROOT_PASS = "12345678";

    private GitLabSessionParameters params;
    private GitLabAPIClient gitLabClient;

    public GitLabService(String host, String root_login, String root_pass, String root_email) {
        ResteasyClient client = new ResteasyClientBuilder().build();
        ResteasyWebTarget target = client.target(host);

        setParams(new GitLabSessionParameters(root_login, root_pass, root_email));
        this.gitLabClient = target.proxy(GitLabAPIClient.class);
    }

    public Collection<PersonVO> getAllPersons() {
        String pToken = getPrivateToken();

        return new PersonConverter().convertAllEntities(gitLabClient.getAllUsers(pToken));
    }

    public PersonVO getPerson(Long id) {
        String pToken = getPrivateToken();

        return new PersonConverter().convertGitLabUserEntity(gitLabClient.getUser(pToken, id));
    }

    public boolean addPerson(PersonVO personVO) {
        String pToken = getPrivateToken();
        Response.Status status = gitLabClient.createUser(pToken, new PersonConverter().convertPerson(personVO));
        return status.getStatusCode() == 201;
    }


    public boolean updatePerson(PersonVO personVO) {
        String pToken = getPrivateToken();
        PersonVO person = new PersonConverter().convertGitLabUserEntity(
                gitLabClient.getUser(pToken, personVO.getId()));

        try {
            person = new PersonConverter().mergePersons(person, personVO);
            Response.Status status = gitLabClient.updateUser(pToken, new PersonConverter().convertPerson(person));
            return status.getStatusCode() == 200;//FIXME check 200 or 201
        } catch (UserRequiredPropertiesIsNotComparable e) {
            System.err.println(e.getMessage());//TODO change on logger
        }

        return false;
    }

    public void removePerson(PersonVO personVO) {
        String pToken = getPrivateToken();

        gitLabClient.removeUser(pToken, personVO.getId());
    }

    public boolean createProject(PersonVO personVO) {
        GitLabProjectEntity defaultProject = getStandartProject(personVO);
        String pToken = getPrivateToken();

        Response.Status status = gitLabClient.createProject(pToken, personVO.getId(), defaultProject);

        return status.getStatusCode() == 201;
    }

    public Collection<GitLabProjectEntity> getAllProjects() {
        String pToken = getPrivateToken();

        return gitLabClient.getAllProjects(pToken);
    }

    public boolean addProjectMember(PersonVO personVO, Integer projId) throws ResourceNotFoundException {
        String pToken = getPrivateToken();
        GitLabProjectMemberEntity projectMember =
                (GitLabProjectMemberEntity) new PersonConverter().convertPerson(personVO);
        projectMember.setAccessLevel(GitLabAccessLevel.Reporter);
        Response.Status status = gitLabClient.addProjectTeamMember(pToken, projectMember, projId);

        if (status.getStatusCode() != 201) {
            throw new ResourceNotFoundException("user or project not found");
        }

        return true;
    }

    public boolean removeProjectMember(PersonVO personVO, GitLabProjectEntity project) throws ResourceNotFoundException {
        String pToken = getPrivateToken();
        Response.Status status = gitLabClient.removeProjectTeamMember(pToken, project.getId(), personVO.getId());
        if (status.getStatusCode() != 200) {
            throw new ResourceNotFoundException("user or project not found");
        }

        return true;
    }

    public GitLabSessionParameters getParams() {
        return params;
    }

    public void setParams(GitLabSessionParameters params) {
        this.params = params;
    }

    private String getPrivateToken() {
        GitLabSessionEntity session = gitLabClient.getSession(params);
        return session.getPrivateToken();
    }

    private GitLabProjectEntity getStandartProject(PersonVO owner) {//FIXME mojno i ubrat' ownera
        GitLabProjectEntity projectEntity = new GitLabProjectEntity();

        projectEntity.setDescription("Personal project");
        //FIXME id???
        projectEntity.setIssuesEnabled(true);
        projectEntity.setMergeRequestsEnabled(false);
        projectEntity.setName("Personal");
        projectEntity.setOwner(new PersonConverter().convertPerson(owner));
        projectEntity.setVisibilityLevel(GitLabProjectEntity.VISIBILITY_PRIVATE_LEVEL);
        projectEntity.setPublic(false);
        projectEntity.setWikiEnabled(true);
        projectEntity.setSnippetsEnabled(true);

        return projectEntity;
    }
}
