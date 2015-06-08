package org.javatraining.model;


import org.javatraining.entity.LessonEntity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by vika on 30.05.15.
 */
public class LessonTypesVO implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String resource;

    private Set<LessonEntity> lessons;

    public Set<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonEntity> lessons) {
        this.lessons = lessons;
    }

    public LessonTypesVO() {
    }

    public LessonTypesVO(Set<LessonEntity> lessonsEntities) {
        this.lessons = lessonsEntities;
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

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonTypesVO)) return false;

        LessonTypesVO that = (LessonTypesVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessons != null ? !lessons.equals(that.lessons) : that.lessons != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }
}
