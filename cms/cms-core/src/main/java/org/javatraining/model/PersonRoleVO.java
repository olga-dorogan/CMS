package org.javatraining.model;

import org.javatraining.entity.PersonRoleEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 30.05.15.
 */
public class PersonRoleVO implements Serializable {

    @NotNull
    private Long id;
    @NotNull
    private String name;

    public PersonRoleVO() {

    }

    public PersonRoleVO(PersonRoleEntity personRoleEntity) {
        this.id = personRoleEntity.getId();
        this.name = personRoleEntity.getName();
    }

    public static PersonRoleEntity convertToEntity(@NotNull PersonRoleVO personRoleVO) {
        PersonRoleEntity entity = new PersonRoleEntity();
        if (personRoleVO.getId() != null) {
            entity.setPersonId(personRoleVO.getId());
        }
        if (personRoleVO.getName() != null) {
            entity.setName(personRoleVO.getName());
        }
        return entity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonRoleVO)) return false;

        PersonRoleVO that = (PersonRoleVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}


