package org.javatraining.ws.service;

import org.javatraining.auth.Auth;
import org.javatraining.config.AuthRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.service.CourseService;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by vika on 02.07.15.
 */
@Path("news")
public class NewsWebService extends AbstractWebService<NewsVO> {
    @EJB
    private CourseService courseService;

    public NewsWebService() {
        super(NewsVO.class);
    }

    @GET
    @Path("{course_id}/news")
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.STUDENT, AuthRole.TEACHER})
    public Response getNews(@PathParam("course_id") Long courseId) {
        return Response.ok(courseService.getAllNewsById(courseId)).build();
    }

    @GET
    @Path("{course_id}/news/{news_id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.STUDENT, AuthRole.TEACHER})
    public Response getLessonByOrderNum(@PathParam("course_id") Long courseId, @PathParam("news_id") Long news_id) {
        return Response.ok(courseService.getNewsByIdFromCourse(courseId, news_id)).build();
    }


    @POST
    @Path("{course_id}/news")
    @Consumes(MediaType.APPLICATION_JSON)
    @Auth(roles = {AuthRole.TEACHER})
    public Response saveLesson(@Context UriInfo uriInfo, @PathParam("course_id") Long courseId, NewsVO newsVO) {
        Response.ResponseBuilder r;
        try {
            newsVO.setId(null); //make sure that there no id set.
            CourseVO courseVO = courseService.getCourseById(courseId);

            courseService.addNewsToCourse(courseVO,newsVO);

            String lessonUri = uriInfo.getRequestUri().toString() + "/" + newsVO.getId();
            r = Response.created(new URI(lessonUri));
        } catch (NullPointerException e) {
            //news wasn't saved
            r = Response.serverError();
        } catch (URISyntaxException e) {
            //this shouldn't happen
            r = Response.serverError();
        }
        return r.build();
    }

}