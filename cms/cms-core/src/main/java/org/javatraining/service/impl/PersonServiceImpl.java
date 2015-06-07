package org.javatraining.service.impl;

import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;
import org.javatraining.service.PersonService;
import org.javatraining.service.exception.UnsupportedOperationException;

import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
@Stateless
public class PersonServiceImpl implements PersonService {
    @Override
    public PersonVO savePerson(@NotNull @Valid PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersonVO updatePerson(@NotNull @Valid PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePerson(@NotNull PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersonVO getPersonById(@NotNull Long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersonVO fillPersonCourses(@NotNull @Valid PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersonVO fillPersonMarks(@NotNull @Valid PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public PersonVO fillPersonForumMessages(@NotNull @Valid PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRoleVO role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<CourseVO> getPersonCourses(@NotNull PersonVO personVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPersonMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updatePersonMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MarkVO> getPersonMarks(@NotNull PersonVO personVO) {
        throw new UnsupportedOperationException();
    }
}
