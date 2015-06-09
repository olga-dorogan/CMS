package org.javatraining.model;

import org.javatraining.entity.PersonRole;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 30.05.15.
 */
public class PersonRoleVO implements Serializable {
    @NotNull
    private PersonRole role;

    public PersonRoleVO() {

    }

    public PersonRoleVO(PersonRole role) {
        this.role = role;
    }

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonRoleVO that = (PersonRoleVO) o;

        return role == that.role;

    }

    @Override
    public int hashCode() {
        return role != null ? role.hashCode() : 0;
    }
}


