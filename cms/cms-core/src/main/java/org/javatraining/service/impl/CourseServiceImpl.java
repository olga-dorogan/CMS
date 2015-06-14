package org.javatraining.service.impl;

import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.model.PersonVO;
import org.javatraining.service.CourseService;
import org.javatraining.service.exception.UnsupportedOperationException;

import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
@Stateless
public class CourseServiceImpl implements CourseService {
    @Override
    public CourseVO save(@NotNull @Valid CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public CourseVO update(@NotNull @Valid CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void remove(@NotNull CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CourseVO> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CourseVO getById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersonsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersonsFromCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersonVO> getAllPersonsFromCourseByRole(@NotNull CourseVO courseVO, @NotNull PersonRole role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NewsVO addNewsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid NewsVO newsVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeNewsFromCourse(@NotNull CourseVO courseVO, @NotNull NewsVO newsVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NewsVO updateNews(@NotNull @Valid NewsVO newsVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public NewsVO getNewsById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<NewsVO> getAllNewsFromCourse(@NotNull CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<NewsVO> getAllNews() {
        throw new UnsupportedOperationException();
    }
}
