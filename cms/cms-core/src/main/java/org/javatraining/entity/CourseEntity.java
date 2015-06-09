package org.javatraining.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * Created by vika on 24.05.15.
 */
@Entity
@Table(name = "courses", schema = "")
public class CourseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public CourseEntity() {
    }

    public CourseEntity(String name, Long owner, String description, Date startdate, Date enddate) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.startdate = startdate;
        this.enddate = enddate;
    }

    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "owner", nullable = true, insertable = true, updatable = true)
    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    @Basic
    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 16777215)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "startdate", nullable = true, insertable = true, updatable = true)
    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    @Basic
    @Column(name = "enddate", nullable = true, insertable = true, updatable = true)
    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    @ManyToMany
    @JoinTable(name = "person_course",
            joinColumns = @JoinColumn(name = "owner_id", referencedColumnName = "owner"),
            inverseJoinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"))
    private Set<PersonEntity> person;

    public Set<PersonEntity> getPerson() {
        return person;
    }

    public void setPerson(Set<PersonEntity> person) {
        this.person = person;
    }


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courses")
    private Set<NewsEntity> news;

    public Set<NewsEntity> getNews() {
        return news;
    }

    public void setNews(Set<NewsEntity> news) {
        this.news = news;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "courses")
    private Set<LessonEntity> lessons;

    public Set<LessonEntity> getLessons() {
        return lessons;
    }

    public void setLessons(Set<LessonEntity> lessons) {
        this.lessons = lessons;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourseEntity)) return false;

        CourseEntity that = (CourseEntity) o;

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