package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * Created by vika on 24.05.15.
 */
@Entity

@Table(name = "lesson", schema = "")
@NamedQueries({
    @NamedQuery(name = "Lesson.FindByCourseId", query = "SELECT l FROM LessonEntity l WHERE l.course.id = :course_id"),
    @NamedQuery(name = "Lesson.FindByCourseIdAndOrderNum", query = "SELECT l FROM LessonEntity l WHERE l.course.id = :course_id AND l.orderNum = :order_num")
})
public class LessonEntity implements Serializable {
    public static String FIND_BY_COURSE = "Lesson.FindByCourseId";
    public static String FIND_BY_COURSE_AND_ORDER_NUM = "Lesson.FindByCourseIdAndOrderNum";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private Long id;

    @Basic
    @Column(name = "type", nullable = true, insertable = true, updatable = true)
    private Long type;

    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic
    @Column(name = "order_num", nullable = true, insertable = true, updatable = true)
    private Long orderNum;

    @NotNull
    @Basic
    @Column(name = "topic", nullable = true, insertable = true, updatable = true, length = 255)
    private String topic;

    @NotNull
    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 16777215)
    private String description;

    @NotNull
    @Basic
    @Column(name = "date", nullable = true, insertable = true, updatable = true, length = 255)
    private Date createDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lesson")
    private Set<LessonLinkEntity> lessonLinks;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity course;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lessons")
    private Set<ForumMessagesEntity> forumMessages;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lesson")
    private Set<PracticeLessonEntity> practiceLesson;

    public LessonEntity() {
    }

    public LessonEntity(Long type, Long orderNum, String topic, String description,
                        Date createDate, CourseEntity course) {
        this.type = type;
        this.orderNum = orderNum;
        this.topic = topic;
        this.description = description;
        this.createDate = createDate;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CourseEntity getCourse() {
        return course;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public Set<PracticeLessonEntity> getPracticeLesson() {
        return practiceLesson;
    }

    public void setPracticeLesson(Set<PracticeLessonEntity> practiceLesson) {
        this.practiceLesson = practiceLesson;
    }

    public Set<LessonLinkEntity> getLessonLinks() {
        return lessonLinks;
    }

    public void setLessonLinks(Set<LessonLinkEntity> lessonLinks) {
        this.lessonLinks = lessonLinks;
    }

    public Set<ForumMessagesEntity> getForumMessages() {
        return forumMessages;
    }

    public void setForumMessages(Set<ForumMessagesEntity> forumMessages) {
        this.forumMessages = forumMessages;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonEntity)) return false;

        LessonEntity that = (LessonEntity) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (course != null ? !course.equals(that.course) : that.course != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        return result;
    }
}

