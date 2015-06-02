package org.javatraining.integration.gitlab.interfaces;

import org.javatraining.integration.gitlab.exception.UserNotFoundException;
import org.javatraining.integration.gitlab.model.GitLabUser;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 15:32.
 * For more information you should send mail to codedealerb@gmail.com
 */
public interface GitlabAPI {

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
    public boolean updateUserByUserName(String userName) throws UserNotFoundException;

    //    Deletes a user.
    // Available only for administrators.
    // return json with properties for deleted user and status 200 OK
    //http://localhost/api/v3/users/3?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example
    public void removeUserByUserName(String userName);// throws UserNotFoundException;


}
