package org.javatraining.service.impl;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.PersonDAO;
import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonVO;
import org.javatraining.model.conversion.CourseConverter;
import org.javatraining.model.conversion.PersonConverter;
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
    public PersonVO saveStudent(@NotNull @Valid PersonVO personVO) {
        personVO.setPersonRole(PersonRole.STUDENT);
        PersonEntity entity = PersonConverter.convertVOToEntity(personVO);
        PersonEntity savedEntity = personDAO.save(entity);
        return PersonConverter.convertEntityToVO(savedEntity);
    }

    @Override
    public PersonVO update(@NotNull @Valid PersonVO personVO) {
        PersonEntity entity = PersonConverter.convertVOToEntity(personVO);
        PersonEntity updatedEntity = personDAO.update(entity);
        return PersonConverter.convertEntityToVO(updatedEntity);
    }

    @Override
    public void remove(@NotNull PersonVO personVO) {
        personDAO.removeById(personVO.getId());
    }

    @Override
    public PersonVO getById(@NotNull Long id) {
        PersonEntity personEntity = personDAO.getById(id);
        return PersonConverter.convertEntityToVO(personEntity);
    }

    @Override
    public PersonVO getByEmail(@NotNull String email) {
        PersonEntity personEntity = personDAO.getByEmail(email);
        if (personEntity == null) {
            return null;
        }
        return PersonConverter.convertEntityToVO(personEntity);
    }

    @Override
    public List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRole role) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // TODO: !!! test this method
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity.getPerson().add(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // TODO: !!! test this method
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity.getPerson().remove(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public Set<CourseVO> getCourses(@NotNull PersonVO personVO) {
        PersonEntity personEntity = PersonConverter.convertVOToEntity(personVO);
        // if FetchType is lazy, initiate data loading
        personEntity.getCourse().size();
        return CourseConverter.convertEntitiesToVOs(personEntity.getCourse());
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
