package org.javatraining.model;

import org.javatraining.entity.PersonEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by vika on 30.05.15.
 */
public class PersonVO implements Serializable {
    private Long id;
    @NotNull
    private String name;
    private String secondName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    private PersonRoleVO personRole;
    private Set<MarkVO> marks;
    private Set<CourseVO> courses;
    private Set<ForumMessagesVO> forumMessages;

    public PersonVO() {
    }

    public PersonVO(Long id, String name, String lastName, String email, PersonRoleVO personRole) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.personRole = personRole;
    }

    public PersonVO(@NotNull PersonEntity personEntity) {
        this.id = personEntity.getId();
        this.name = personEntity.getName();
        this.secondName = personEntity.getSecondName();
        this.lastName = personEntity.getLastName();
        this.email = personEntity.getEmail();
        if (personEntity.getPersonRole() != null && !personEntity.getPersonRole().isEmpty()) {
            this.personRole = new PersonRoleVO(personEntity.getPersonRole().iterator().next());
        }
        //TODO: convert collections of marks, courses and messages
    }

    public static PersonEntity convertToEntity(@NotNull PersonVO personVO) {
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
            personEntity.setPersonRole(Collections.singleton(PersonRoleVO.convertToEntity(personVO.getPersonRole())));
        }
        // TODO: set person marks, courses and messages
        return personEntity;
    }

    public static Set<PersonVO> convertEntitiesToVOs(@NotNull Collection<PersonEntity> personEntities) {
        Set<PersonVO> personVOs = new HashSet<>(personEntities.size());
        for (PersonEntity personEntity : personEntities) {
            personVOs.add(new PersonVO(personEntity));
        }
        return personVOs;
    }

    public static Set<PersonEntity> convertVOsToEntities(@NotNull Collection<PersonVO> personVOs) {
        Set<PersonEntity> personEntities = new HashSet<>(personVOs.size());
        for (PersonVO personVO : personVOs) {
            personEntities.add(convertToEntity(personVO));
        }
        return personEntities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PersonRoleVO getPersonRole() {
        return personRole;
    }

    public void setPersonRole(PersonRoleVO personRole) {
        this.personRole = personRole;
    }

    public Set<MarkVO> getMarks() {
        return marks;
    }

    public void setMarks(Set<MarkVO> marks) {
        this.marks = marks;
    }

    public Set<CourseVO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseVO> courses) {
        this.courses = courses;
    }


    public Set<ForumMessagesVO> getForumMessages() {
        return forumMessages;
    }

    public void setForumMessages(Set<ForumMessagesVO> forumMessages) {
        this.forumMessages = forumMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonVO that = (PersonVO) o;

        if (id != that.id) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;

        return true;
    }


    @Override
    public int hashCode() {
        int result = (int) (name != null ? (id ^ (id >>> 32)) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
