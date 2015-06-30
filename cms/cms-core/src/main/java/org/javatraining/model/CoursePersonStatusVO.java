package org.javatraining.model;

import org.javatraining.entity.enums.CourseStatus;

/**
 * Created by vika on 27.06.15.
 */
public class CoursePersonStatusVO {

    private Long id;

    private CourseStatus courseStatus;

    private CourseVO course;

    private PersonVO person;

    public CoursePersonStatusVO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public CourseVO getCourse() {
        return course;
    }

    public void setCourse(CourseVO course) {
        this.course = course;
    }

    public PersonVO getPerson() {
        return person;
    }

    public void setPerson(PersonVO person) {
        this.person = person;
    }
}
