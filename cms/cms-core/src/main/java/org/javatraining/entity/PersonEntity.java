package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "person", schema = "")
public class PersonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @NotNull
    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 255)
    private String name;

    @NotNull
    @Basic
    @Column(name = "second_name", nullable = true, insertable = true, updatable = true, length = 255)
    private String secondName;

    @NotNull
    @Basic
    @Column(name = "last_name", nullable = true, insertable = true, updatable = true, length = 255)
    private String lastName;

    @NotNull
    @Basic
    @Column(name = "email", nullable = true, insertable = true, updatable = true, length = 255)
    private String email;

    @Enumerated(EnumType.STRING)
    private PersonRole personRole;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persons")
    private Set<ForumMessagesEntity> forumMassages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "persons")
    private Set<MarkEntity> marks;


    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER, mappedBy = "person")
    private Set<CourseEntity> course;

    public PersonEntity() {
    }

    public PersonEntity(String name, String secondName, String lastName, String email, PersonRole personRole) {
        this.name = name;
        this.secondName = secondName;
        this.lastName = lastName;
        this.email = email;
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

    public void setForumMassages(Set<ForumMessagesEntity> forumMassages) {
        this.forumMassages = forumMassages;
    }

    public Set<ForumMessagesEntity> getForumMassages() {
        return forumMassages;
    }

    public PersonRole getPersonRole() {
        return personRole;
    }

    public void setPersonRole(PersonRole personRole) {
        this.personRole = personRole;
    }

    public Set<MarkEntity> getMarks() {
        return marks;
    }

    public void setMarks(Set<MarkEntity> marks) {
        this.marks = marks;
    }

    public Set<CourseEntity> getCourse() {
        return course;
    }

    public void setCourse(Set<CourseEntity> course) {
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
        if (!(o instanceof PersonEntity)) return false;

        PersonEntity that = (PersonEntity) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (personRole != that.personRole) return false;
        if (secondName != null ? !secondName.equals(that.secondName) : that.secondName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (id == null) ? 0 : (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (personRole != null ? personRole.hashCode() : 0);
        return result;
    }
}
