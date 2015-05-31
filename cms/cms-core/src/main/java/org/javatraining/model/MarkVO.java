package org.javatraining.model;


import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.PersonEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 30.05.15.
 */
public class MarkVO implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private Integer lessonId;
    @NotNull
    private Integer mark;

    public MarkVO() {
    }

    private PersonEntity persons;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLessonId() {
        return lessonId;
    }

    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MarkVO)) return false;

        MarkVO that = (MarkVO) o;

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
