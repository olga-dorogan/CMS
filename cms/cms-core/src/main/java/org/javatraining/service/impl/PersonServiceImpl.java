package org.javatraining.service.impl;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.MarkDAO;
import org.javatraining.dao.PersonDAO;
import org.javatraining.dao.PracticeLessonDAO;
import org.javatraining.dao.exception.EntityNotExistException;
import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.MarkEntity;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.PracticeLessonEntity;
import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonVO;
import org.javatraining.model.PracticeLessonVO;
import org.javatraining.model.conversion.MarkConverter;
import org.javatraining.model.conversion.PersonConverter;
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
    @EJB
    private PracticeLessonDAO practiceLessonDAO;

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
            updatePersonVO(personVO, personVOFromEmail);
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
        try {
            PersonEntity personEntity = personDAO.getById(id);
            return PersonConverter.convertEntityToVO(personEntity);
        } catch (EntityNotExistException e) {
            return null;
        }
    }

    @Override
    public PersonVO getByEmail(@NotNull String email) {
        try {
            PersonEntity personEntity = personDAO.getByEmail(email);
            return PersonConverter.convertEntityToVO(personEntity);
        } catch (EntityNotExistException e) {
            return null;
        }
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
//        courseEntity.getPersons().add(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO) {
        // owning side is CourseEntity, so all operations need to be from CourseEntity
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
//        courseEntity.getPersons().remove(personEntity);
        courseDAO.update(courseEntity);
    }

    @Override
    public Set<CourseVO> getCourses(@NotNull PersonVO personVO) {
        PersonEntity personEntity = personDAO.getById(personVO.getId());
//        return CourseConverter.convertEntitiesToVOs(personEntity.getCourse());
     throw new UnsupportedOperationException();
    }

    @Override
    public void setMark(@NotNull PersonVO personVO, @NotNull PracticeLessonVO practiceLessonVO, @NotNull @Valid MarkVO markVO) {
        PersonEntity personEntity = personDAO.getById(personVO.getId());
        PracticeLessonEntity lessonEntity = practiceLessonDAO.getById(practiceLessonVO.getId());
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

    private void updatePersonVO(PersonVO personToUpdate, PersonVO personWithUpdateInfo) {
        personToUpdate.setId(personWithUpdateInfo.getId());
        personToUpdate.setEmail(personWithUpdateInfo.getEmail());
        personToUpdate.setPersonRole(personWithUpdateInfo.getPersonRole());
        personToUpdate.setName(personWithUpdateInfo.getName());
        personToUpdate.setSecondName(personWithUpdateInfo.getSecondName());
        personToUpdate.setLastName(personWithUpdateInfo.getLastName());
    }
}
