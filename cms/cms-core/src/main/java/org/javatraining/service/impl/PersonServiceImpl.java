package org.javatraining.service.impl;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.PersonDAO;
import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;
import org.javatraining.service.PersonService;
import org.javatraining.service.exception.UnsupportedOperationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by olga on 07.06.15.
 */
@Stateless
public class PersonServiceImpl implements PersonService {
    @EJB
    private PersonDAO personDAO;
    @EJB
    private CourseDAO courseDAO;

    @Override
    public PersonVO save(@NotNull @Valid PersonVO personVO) {
        personVO.setPersonRole(new PersonRoleVO(PersonRole.STUDENT));
        return new PersonVO(personDAO.save(PersonVO.convertToEntity(personVO)));
    }

    @Override
    public PersonVO update(@NotNull @Valid PersonVO personVO) {
        return new PersonVO(personDAO.update(PersonVO.convertToEntity(personVO)));
    }

    @Override
    public void remove(@NotNull PersonVO personVO) {
        personDAO.removeById(personVO.getId());
    }

    @Override
    public PersonVO getById(@NotNull Long id) {
        return new PersonVO(personDAO.getById(id));
    }

    @Override
    public PersonVO getByEmail(@NotNull String email) {
        return new PersonVO(personDAO.getByEmail(email));
    }

    @Override
    public List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRoleVO role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // TODO: !!! test this method
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = PersonVO.convertToEntity(personVO);
        CourseEntity courseEntity = CourseVO.convertToEntity(courseVO);
        courseEntity.getPerson().add(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // TODO: !!! test this method
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = PersonVO.convertToEntity(personVO);
        CourseEntity courseEntity = CourseVO.convertToEntity(courseVO);
        courseEntity.getPerson().remove(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public Set<CourseVO> getCourses(@NotNull PersonVO personVO) {
        PersonEntity personEntity = PersonVO.convertToEntity(personVO);
        // if FetchType is lazy, initiate data loading
        personEntity.getCourse().size();
        return CourseVO.convertEntitiesToVOs(personEntity.getCourse());
    }

    @Override
    public void setMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void updateMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<MarkVO> getMarks(@NotNull PersonVO personVO) {
        throw new UnsupportedOperationException();
    }
}
