package org.javatraining.integration.gitlab.api.interfaces;

import org.javatraining.integration.gitlab.api.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.api.model.GitLabSession;
import org.javatraining.integration.gitlab.api.model.GitLabUser;

import java.net.URL;
import java.util.Map;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 15:32.
 * For more information you should send mail to codedealerb@gmail.com
 */
public interface GitlabAPI {

    public URL getApiUrl(Map<String, String> urlTail);

    //get authenticated user
    //get /user
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root example
    public GitLabUser getUserByUserName(String userName) throws UserNotFoundException;

    //gitlab usr CRUD
    //user creation (only for admin)
    //post /users - rest method
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example without properties
    //return status 201 if created
    public boolean createNewGitLabUser(GitLabUser userProperties);

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

    public boolean createNewProjectForUser(GitLabUser user) throws UserNotFoundException;

    public boolean addProjectTeamMember()
}
