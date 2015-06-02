package org.javatraining.integration.gitlab;

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
    public boolean createNewGitLabUser(GitLabUser userToPersis);

    //modification to existing user (only for admin)
    //put /users - rest method
    public boolean updateUserByUserName(String userName) throws UserNotFoundException;

    //    Deletes a user.
    // Available only for administrators.
    // This is an idempotent function, calling this function for
    // a non-existent user id still returns a status code 200 OK.
    // The JSON response differs if the user was actually deleted or not.
    // In the former the user is returned and in the latter not.
    public void removeUserByUserName(String userName) throws UserNotFoundException;


}
