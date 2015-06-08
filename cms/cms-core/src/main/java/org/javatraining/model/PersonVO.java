package org.javatraining.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by vika on 30.05.15.
 */
public class PersonVO implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String secondName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    private Set<PersonRoleVO> personRole;
    private Set<MarkVO> marks;
    private Set<CourseVO> course;

    public PersonVO() {
    }

    public PersonVO(Set<PersonRoleVO> personRole) {
        this.personRole = personRole;
    }

    public Set<MarkVO> getMarks() {
        return marks;
    }

    public void setMarks(Set<MarkVO> marks) {
        this.marks = marks;
    }

    public Set<PersonRoleVO> getPersonRole() {
        return personRole;
    }

    public void setPersonRole(Set<PersonRoleVO> personRole) {
        this.personRole = personRole;
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

    public Set<CourseVO> getCourse() {
        return course;
    }

    public void setCourse(Set<CourseVO> course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
