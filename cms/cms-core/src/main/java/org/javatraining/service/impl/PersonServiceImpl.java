package org.javatraining.service.impl;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.MarkDAO;
import org.javatraining.dao.PersonDAO;
import org.javatraining.entity.*;
import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonVO;
import org.javatraining.model.PracticeLessonVO;
import org.javatraining.model.conversion.CourseConverter;
import org.javatraining.model.conversion.MarkConverter;
import org.javatraining.model.conversion.PersonConverter;
import org.javatraining.model.conversion.PracticeLessonConverter;
import org.javatraining.service.PersonService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    @EJB
    private MarkDAO markDAO;

    @Override
    public void saveStudent(@NotNull @Valid PersonVO personVO) {
        personVO.setPersonRole(PersonRole.STUDENT);
        PersonEntity entity = PersonConverter.convertVOToEntity(personVO);
        PersonEntity savedEntity = personDAO.save(entity);
        personVO.setId(savedEntity.getId());
    }

    @Override
    public void save(@NotNull @Valid PersonVO personVO) {
        PersonVO personVOFromEmail = getByEmail(personVO.getEmail());
        if (personVOFromEmail != null) {
            personVO.setPersonRole(personVOFromEmail.getPersonRole());
            personVO.setId(personVOFromEmail.getId());
        } else {
            saveStudent(personVO);
        }
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
        if (personEntity == null) {
            return null;
        }
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
        List<PersonEntity> personEntities = personDAO.getByPersonRole(role);
        return new ArrayList<>(PersonConverter.convertEntitiesToVOs(personEntities));
    }

    @Override
    public void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity.getPersons().add(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity.getPersons().remove(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public Set<CourseVO> getCourses(@NotNull PersonVO personVO) {
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        return CourseConverter.convertEntitiesToVOs(personEntity.getCourse());
    }

    @Override
    public void setMark(@NotNull PersonVO personVO, @NotNull PracticeLessonVO practiceLessonVO, @NotNull @Valid MarkVO markVO) {
        PersonEntity personEntity = PersonConverter.convertVOToEntity(personVO);
        PracticeLessonEntity lessonEntity = PracticeLessonConverter.convertVOToEntity(practiceLessonVO);
        MarkEntity markEntity = MarkConverter.convertVOToEntity(markVO);
        markDAO.save(markEntity, personEntity, lessonEntity);
    }

    @Override
    public void removeMark(@NotNull MarkVO markVO) {
        markDAO.removeById(markVO.getId());
    }

    @Override
    public List<MarkVO> getMarks(@NotNull PersonVO personVO) {
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        Set<MarkEntity> marks = personEntity.getMarks();
        return MarkConverter.convertEntitiesToVOs(marks);
    }
}
