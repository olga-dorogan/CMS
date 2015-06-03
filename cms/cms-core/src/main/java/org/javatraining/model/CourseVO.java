package org.javatraining.model;


import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * Created by vika on 29.05.15.
 */
public class CourseVO implements Serializable {

    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private Long owner;
    @NotNull
    private String description;
    @NotNull
    private Date startdate;
    @NotNull
    private Date enddate;

    public CourseVO() {
    }

    private Set<PersonVO> person;

    private Set<NewsVO> news;

    private Set<LessonVO> lessons;

    public Set<LessonVO> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonVO> lessons) {
        this.lessons = lessons;
    }

    public Set<NewsVO> getNews() {
        return news;
    }

    public void setNews(Set<NewsVO> news) {
        this.news = news;
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

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    public Set<PersonVO> getPerson() {
        return person;
    }

    public void setPerson(Set<PersonVO> person) {
        this.person = person;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseVO)) return false;

        CourseVO that = (CourseVO) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (enddate != null ? !enddate.equals(that.enddate) : that.enddate != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (lessons != null ? !lessons.equals(that.lessons) : that.lessons != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (news != null ? !news.equals(that.news) : that.news != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (person != null ? !person.equals(that.person) : that.person != null) return false;
        if (startdate != null ? !startdate.equals(that.startdate) : that.startdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startdate != null ? startdate.hashCode() : 0);
        result = 31 * result + (enddate != null ? enddate.hashCode() : 0);
        result = 31 * result + (person != null ? person.hashCode() : 0);
        result = 31 * result + (news != null ? news.hashCode() : 0);
        result = 31 * result + (lessons != null ? lessons.hashCode() : 0);
        return result;
    }
}
