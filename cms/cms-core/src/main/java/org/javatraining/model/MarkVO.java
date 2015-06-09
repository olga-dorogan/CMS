package org.javatraining.model;


import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by vika on 30.05.15.
 */
public class MarkVO implements Serializable {
    private Long id;
    @NotNull
    private Integer lessonId;
    @NotNull
    private Integer mark;

    public MarkVO() {
    }

    public MarkVO(Long id, Integer lessonId, Integer mark) {
        this.id = id;
        this.lessonId = lessonId;
        this.mark = mark;
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
        if (o == null || getClass() != o.getClass()) return false;

        MarkVO markVO = (MarkVO) o;

        if (!id.equals(markVO.id)) return false;
        if (!lessonId.equals(markVO.lessonId)) return false;
        return mark.equals(markVO.mark);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + lessonId.hashCode();
        result = 31 * result + mark.hashCode();
        return result;
    }
}
