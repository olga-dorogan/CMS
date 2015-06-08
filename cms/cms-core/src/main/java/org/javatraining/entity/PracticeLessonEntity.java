package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "practice_lesson", schema = "")
public class PracticeLessonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String task;

    public PracticeLessonEntity() {
    }

    public PracticeLessonEntity(String task,LessonEntity lesson) {
        this.task = task;
        this.lesson = lesson;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "task", nullable = false, insertable = true, updatable = true, length = 255)
    public String getTask() {
        return task;
    }
    public void setTask(String task) {
        this.task = task;
    }

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "lesson_id", nullable = false, insertable = false, updatable = false)
    private LessonEntity lesson;
    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PracticeLessonEntity)) return false;

        PracticeLessonEntity that = (PracticeLessonEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lesson != null ? !lesson.equals(that.lesson) : that.lesson != null) return false;
        if (task != null ? !task.equals(that.task) : that.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (lesson != null ? lesson.hashCode() : 0);
        return result;
    }
}
