package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "forum_messages", schema = "", catalog = "cms")
public class ForumMassagesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private Long id;
    @NotNull
    private Integer parentId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private Integer lessonId;
    @NotNull
    private Timestamp date;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "lesson_id", nullable = false)
    private LessonEntity lessons;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})

    @JoinColumn(name = "author", nullable = false)
    private PersonEntity persons;

    public ForumMassagesEntity() {
    }

    public LessonEntity getLessons() {
        return lessons;
    }

    public void setLessons(LessonEntity lessons) {
        this.lessons = lessons;
    }

    public PersonEntity getPersons() {
        return persons;
    }

    public void setPersons(PersonEntity persons) {
        this.persons = persons;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "parent_id", nullable = true, insertable = true, updatable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 16777215)
    public String getDescription() {
        return description;
    }

    public void setDescription(String content) {
        this.description = content;
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
    @Column(name = "date", nullable = true, insertable = true, updatable = true)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ForumMassagesEntity)) return false;

        ForumMassagesEntity that = (ForumMassagesEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessonId != null ? !lessonId.equals(that.lessonId) : that.lessonId != null) return false;
        if (lessons != null ? !lessons.equals(that.lessons) : that.lessons != null) return false;
        if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) return false;
        if (persons != null ? !persons.equals(that.persons) : that.persons != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (lessonId != null ? lessonId.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        result = 31 * result + (persons != null ? persons.hashCode() : 0);
        return result;
    }


}
