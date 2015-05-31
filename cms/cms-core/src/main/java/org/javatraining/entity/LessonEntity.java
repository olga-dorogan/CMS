package org.javatraining.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "lessons", schema = "")
public class LessonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long courseId;
    private Long type;
    private Integer orderNum;
    private String topic;
    private String content;
    private Date createDate;


    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity courses;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "type", nullable = false, insertable = false, updatable = false)
    private LessonTypeEntity lessonTypes;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lesson")
    private Set<LessonLinkEntity> lessonLinks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lessons")
    private Set<MarkEntity> marks;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "lessons")
    private Set<ForumMassagesEntity> forumMessages;

    public CourseEntity getCourses() {
        return courses;
    }

    public void setCourses(CourseEntity courses) {
        this.courses = courses;
    }

    public LessonTypeEntity getLessonTypes() {
        return lessonTypes;
    }

    public void setLessonTypes(LessonTypeEntity lessonTypes) {
        this.lessonTypes = lessonTypes;
    }

    public Set<LessonLinkEntity> getLessonLinks() {
        return lessonLinks;
    }

    public void setLessonLinks(Set<LessonLinkEntity> lessonLinks) {
        this.lessonLinks = lessonLinks;
    }

    public Set<MarkEntity> getMarks() {
        return marks;
    }

    public void setMarks(Set<MarkEntity> marks) {
        this.marks = marks;
    }

    public Set<ForumMassagesEntity> getForumMessages() {
        return forumMessages;
    }

    public void setForumMessages(Set<ForumMassagesEntity> forumMessages) {
        this.forumMessages = forumMessages;
    }

    @Basic
    @Column(name = "course_id", nullable = false, insertable = true, updatable = true)
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "type", nullable = false, insertable = false, updatable = false)
    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Basic
    @Column(name = "order_num", nullable = true, insertable = true, updatable = true)
    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Basic
    @Column(name = "topic", nullable = true, insertable = true, updatable = true, length = 255)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "date", nullable = true, insertable = true, updatable = true, length = 255)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date topic) {
        this.createDate = topic;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 16777215)
    public String getDescription() {
        return content;
    }

    public void setDescription(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LessonEntity)) return false;

        LessonEntity that = (LessonEntity) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (courseId != null ? !courseId.equals(that.courseId) : that.courseId != null) return false;
        if (courses != null ? !courses.equals(that.courses) : that.courses != null) return false;
        if (forumMessages != null ? !forumMessages.equals(that.forumMessages) : that.forumMessages != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessonLinks != null ? !lessonLinks.equals(that.lessonLinks) : that.lessonLinks != null) return false;
        if (lessonTypes != null ? !lessonTypes.equals(that.lessonTypes) : that.lessonTypes != null) return false;
        if (marks != null ? !marks.equals(that.marks) : that.marks != null) return false;
        if (orderNum != null ? !orderNum.equals(that.orderNum) : that.orderNum != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (orderNum != null ? orderNum.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (courses != null ? courses.hashCode() : 0);
        result = 31 * result + (lessonTypes != null ? lessonTypes.hashCode() : 0);
        result = 31 * result + (lessonLinks != null ? lessonLinks.hashCode() : 0);
        result = 31 * result + (marks != null ? marks.hashCode() : 0);
        result = 31 * result + (forumMessages != null ? forumMessages.hashCode() : 0);
        return result;
    }
}
