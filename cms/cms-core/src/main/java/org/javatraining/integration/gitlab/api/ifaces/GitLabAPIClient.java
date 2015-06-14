package org.javatraining.integration.gitlab.api.ifaces;

import org.javatraining.integration.gitlab.api.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 15:32.
 * For more information you should send mail to codedealerb@gmail.com
 */
@Path("/api/v3")
public interface GitLabAPIClient {

    @GET
    @Path("/users")
    @Produces("application/json")
    Collection<GitLabUser> getAllUsers(@QueryParam("privateToken") String privateToken,
                                       @QueryParam("sudo") String sudo);

    //get authenticated user
    //get /user
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root example
    @GET
    @Path("/users/{id}")
    @Produces("application/json")
    GitLabUser getUser(@QueryParam("privateToken") String privateToken,
                       @QueryParam("sudo") String sudo,
                       @PathParam("userName") Long id);

    //gitlab usr CRUD
    //user creation (only for admin)
    //post /users - rest method
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example without properties
    //return status 201 if created
    @POST
    @Path("/users")
    @Consumes("application/json")
    Response.Status createUser(@QueryParam("privateToken") String privateToken,
                               @QueryParam("sudo") String sudo,
                               GitLabUser userProperties);

    //modification to existing user (only for admin)
    //put /users - rest methodroot_
    //http://localhost/api/v3/users?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example without properties
    @PUT
    @Path("/users")
    @Consumes("application/json")
    Response.Status updateUser(@QueryParam("privateToken") String privateToken,
                               @QueryParam("sudo") String sudo,
                               GitLabUser userProperties);

    //    Deletes a user.
    // Available only for administrators.
    // return json with properties for deleted user and status 200 OK
    //http://localhost/api/v3/users/3?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example
    @DELETE
    @Path("/users/{id}")
    Response.Status removeUser(@QueryParam("privateToken") String privateToken,
                               @QueryParam("sudo") String sudo,
                               @PathParam("id") Long id);

    //get session for manage user's account with special private token
    //POST /session
    //username, email, password for authenticated user
    //return 401 unauthorized or 201 with json where with all properties we can get pToken
    //http://localhost/api/v3/session?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root - example
    @POST
    @Path("/session")
    @Consumes("application/json")
    @Produces("application/json")
    GitLabSession getSession(GitLabSessionParameters parameters, @QueryParam("sudo") String sudo);

//    @POST
//    @Path("/session?sudo=root")
//    @Consumes("application/json")
//    @Produces("application/json")
//    Response.Status getSession(String login, String password, String email);
    //create new project for sspecified user
    //post /projects/user/:user_id
    //http://localhost/api/v3/projects/user/2?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    //return 404 or 201 created with project json properties
    @POST
    @Path("/projects/user/{id}")
    @Consumes("application/json")
    Response.Status createProject(@QueryParam("privateToken") String privateToken,
                                  @QueryParam("sudo") String sudo,
                                  @PathParam("id") Long id,
                                  GitLabProject projectProperties);

    @GET
    @Path("/projects/all")
    @Produces("application/json")
    Collection<GitLabProject> getAllProjects(@QueryParam("privateToken") String privateToken,
                                             @QueryParam("sudo") String sudo);


    //add specified user to project membership
    //post /projects/:proj_id/members
    //http://localhost/api/v3/projects/6/members?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    //need gitlabprojectmember with access_level and project id
    //return 401, 400, if bad request, 201 created with json of simplified user with access level
    @POST
    @Path("/projects/{proj_id}/members")
    @Consumes("application/json")
    Response.Status addProjectTeamMember(@QueryParam("privateToken") String privateToken,
                                         @QueryParam("sudo") String sudo,
                                         GitLabProjectMember projectMemberToAdd,
                                         @PathParam("proj_id") Integer projectId);

    //delete /projects/:proj_id/members/:user_id_to_remove
    //200 if ok
    //http://localhost/api/v3/projects/6/members/2?private_token=xTApBC_xvpkKEw7yHjDV&sudo=root
    @DELETE
    @Path("/projects/{proj_id}/members/{user_id}")
    Response.Status removeProjectTeamMember(@QueryParam("privateToken") String privateToken,
                                            @QueryParam("sudo") String sudo,
                                            @PathParam("proj_id") Integer projectId,
                                            @PathParam("user_id") Long userId);
}
