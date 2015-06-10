package org.javatraining.model.conversion;

import org.javatraining.entity.PersonEntity;
import org.javatraining.model.PersonVO;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by olga on 10.06.15.
 */
public class PersonConverter {

    public static PersonVO convertEntityToVO(@NotNull PersonEntity personEntity) {
        PersonVO personVO = new PersonVO(personEntity.getId(), personEntity.getName(), personEntity.getLastName(),
                personEntity.getEmail(), personEntity.getPersonRole());
        personVO.setSecondName(personEntity.getSecondName());
        if (personEntity.getCourse() != null) {
            personVO.setCourses(CourseConverter.convertEntitiesToVOs(personEntity.getCourse()));
        }
        //TODO: convert collections of marks and messages
        return personVO;
    }

    public static PersonEntity convertVOToEntity(@NotNull PersonVO personVO) {
        PersonEntity personEntity = new PersonEntity();
        if (personVO.getId() != null) {
            personEntity.setId(personVO.getId());
        }
        if (personVO.getName() != null) {
            personEntity.setName(personVO.getName());
        }
        if (personVO.getSecondName() != null) {
            personEntity.setSecondName(personVO.getSecondName());
        }
        if (personVO.getLastName() != null) {
            personEntity.setLastName(personVO.getLastName());
        }
        if (personVO.getEmail() != null) {
            personEntity.setEmail(personVO.getEmail());
        }
        if (personVO.getPersonRole() != null) {
            personEntity.setPersonRole(personVO.getPersonRole());
        }
        if (personVO.getCourses() != null) {
            personEntity.setCourse(CourseConverter.convertVOsToEntities(personVO.getCourses()));
        }
        // TODO: set person marks and messages
        return personEntity;
    }

    public static Set<PersonVO> convertEntitiesToVOs(@NotNull Collection<PersonEntity> personEntities) {
        Set<PersonVO> personVOs = new HashSet<>(personEntities.size());
        for (PersonEntity personEntity : personEntities) {
            personVOs.add(convertEntityToVO(personEntity));
        }
        return personVOs;
    }

    public static Set<PersonEntity> convertVOsToEntities(@NotNull Collection<PersonVO> personVOs) {
        Set<PersonEntity> personEntities = new HashSet<>(personVOs.size());
        for (PersonVO personVO : personVOs) {
            personEntities.add(convertVOToEntity(personVO));
        }
        return personEntities;
    }

    private PersonConverter() {
    }
}
