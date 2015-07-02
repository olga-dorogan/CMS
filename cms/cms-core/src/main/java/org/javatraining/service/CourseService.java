package org.javatraining.service;

import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.model.PersonVO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
public interface CourseService {

    //  Course methods

    CourseVO saveCourse(@NotNull @Valid CourseVO courseVO);

    CourseVO updateCourse(@NotNull @Valid CourseVO courseVO);

    CourseVO removeCourse(@NotNull CourseVO courseVO);

    @NotNull
    @Valid
    List<CourseVO> getAll();

    void clear();
    @Valid
    CourseVO getCourseById(@NotNull Long id);

    //  Course --- Person methods

    CourseVO addPersonsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons);

    CourseVO removePersonsFromCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons);



    @NotNull
    @Valid
    List<PersonVO> getAllPersonsFromCourseByRole(@NotNull CourseVO courseVO, @NotNull PersonRole role);

    //  Course --- News methods; News methods

    NewsVO addNewsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid NewsVO newsVO);

    NewsVO removeNewsFromCourse(@NotNull CourseVO courseVO, @NotNull NewsVO newsVO);

    NewsVO updateNews(@NotNull @Valid NewsVO newsVO);

    NewsVO getNewsById(@NotNull Long id);

    @NotNull
    @Valid
    List<NewsVO> getAllNewsFromCourse(@NotNull CourseVO courseVO);

    @NotNull
    @Valid
    List<NewsVO> getAllNews();

    //TODO: add methods to interact with LessonVO

}
