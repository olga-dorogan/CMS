package org.javatraining.integration.gitlab.api.interfaces;

import org.javatraining.integration.gitlab.api.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.api.model.GitLabProject;
import org.javatraining.integration.gitlab.api.model.GitLabProjectMember;
import org.javatraining.integration.gitlab.api.model.GitLabSession;
import org.javatraining.integration.gitlab.api.model.GitLabUser;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 15:32.
 * For more information you should send mail to codedealerb@gmail.com
 */
public interface GitLabAPI {

    public URL getApiUrl(Map<String, String> urlTail, String methodData) throws MalformedURLException;

    public Collection<GitLabUser> getAllUsersFromGitlab();

    //get authenticated user
    //get /user
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root example
    public GitLabUser getUserByUserName(String userName) throws UserNotFoundException;

    //gitlab usr CRUD
    //user creation (only for admin)
    //post /users - rest method
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example without properties
    //return status 201 if created
    public boolean createGitLabUser(GitLabUser userProperties);

    //modification to existing user (only for admin)
    //put /users - rest method
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example without properties
    public void updateUserByUserName(String userName) throws UserNotFoundException;

    //    Deletes a user.
    // Available only for administrators.
    // return json with properties for deleted user and status 200 OK
    //http://localhost/api/v3/users/3?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example
    public void removeUserByUserName(String userName);//FIXME idempotent function, add or not throws UserNotFoundException;

    //get session for manage user's account with special private token
    //POST /session
    //username, email, password for authenticated user
    //return 401 unauthorized or 201 with json where with all properties we can get pToken
    //http://localhost/api/v3/session?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example
    public GitLabSession getGitlabSessionForSpecifiedUser(String userName, String email, String password) throws UserNotFoundException;

    //create new project for sspecified user
    //post /projects/user/:user_id
    //http://localhost/api/v3/projects/user/2?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    //return 404 or 201 created with project json properties
    public boolean createNewProjectForUser(GitLabUser user) throws UserNotFoundException;

    //add specified user to project membership
    //post /projects/:proj_id/members
    //http://localhost/api/v3/projects/6/members?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    //need gitlabprojectmember with access_level and project id
    //return 401, 400, if bad request, 201 created with json of simplified user with access level
    public boolean addProjectTeamMember(GitLabProjectMember projectMemberToAdd, GitLabProject project) throws UserNotFoundException;

    //post /projects/:proj_id/members/:user_id_to_remove
    //200 if ok
    //http://localhost/api/v3/projects/6/members/2?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    public void removeProjectTeamMember(GitLabProject project, GitLabProjectMember projectMemberToRemove) throws UserNotFoundException;
}
