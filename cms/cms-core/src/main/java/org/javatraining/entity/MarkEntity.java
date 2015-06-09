package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "marks", schema = "")
public class MarkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private Long mark;

    public MarkEntity() {
    }

    public MarkEntity(Long mark) {
           this.mark = mark;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Basic
    @Column(name = "mark", nullable = true, insertable = true, updatable = true)
    public Long getMark() {
        return mark;
    }

    public void setMark(Long mark) {
        this.mark = mark;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity persons;

    public PersonEntity getPersons() {
        return persons;
    }

    public void setPersons(PersonEntity persons) {
        this.persons = persons;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessons;

    public LessonEntity getLessons() {
        return lessons;
    }

    public void setLessons(LessonEntity lessons) {
        this.lessons = lessons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkEntity)) return false;

        MarkEntity that = (MarkEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessons != null ? !lessons.equals(that.lessons) : that.lessons != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (persons != null ? !persons.equals(that.persons) : that.persons != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }
}
