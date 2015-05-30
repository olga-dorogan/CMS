package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "marks", schema = "", catalog = "cms")
public class MarkEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotNull
    private Integer lessonId;
    @NotNull
    private Integer mark;

    public MarkEntity() {
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "person_id", nullable = false)
    private PersonEntity persons;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessons;

    public PersonEntity getPersons() {
        return persons;
    }

    public void setPersons(PersonEntity persons) {
        this.persons = persons;
    }

    public LessonEntity getLessons() {
        return lessons;
    }

    public void setLessons(LessonEntity lessons) {
        this.lessons = lessons;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lesson_id", nullable = true, insertable = true, updatable = true)
    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    @Basic
    @Column(name = "mark", nullable = true, insertable = true, updatable = true)
    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkEntity)) return false;

        MarkEntity that = (MarkEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessonId != null ? !lessonId.equals(that.lessonId) : that.lessonId != null) return false;
        if (lessons != null ? !lessons.equals(that.lessons) : that.lessons != null) return false;
        if (mark != null ? !mark.equals(that.mark) : that.mark != null) return false;
        if (persons != null ? !persons.equals(that.persons) : that.persons != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (mark != null ? mark.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }
}
