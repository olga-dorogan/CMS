package org.javatraining.service;

import org.javatraining.entity.PersonRole;
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

    CourseVO save(@NotNull @Valid CourseVO courseVO);

    CourseVO update(@NotNull @Valid CourseVO courseVO);

    void remove(@NotNull CourseVO courseVO);

    @NotNull
    @Valid
    List<CourseVO> getAll();

    @Valid
    CourseVO getById(@NotNull Long id);

    //  Course --- Person methods

    void addPersonsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons);

    void removePersonsFromCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons);

    @NotNull
    @Valid
    List<PersonVO> getAllPersonsFromCourseByRole(@NotNull CourseVO courseVO, @NotNull PersonRole role);

    //  Course --- News methods; News methods

    NewsVO addNewsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid NewsVO newsVO);

    void removeNewsFromCourse(@NotNull CourseVO courseVO, @NotNull NewsVO newsVO);

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
