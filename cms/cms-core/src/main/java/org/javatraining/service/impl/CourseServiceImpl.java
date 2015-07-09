package org.javatraining.service.impl;

import org.javatraining.dao.*;
import org.javatraining.entity.*;
import org.javatraining.entity.enums.CourseStatus;
import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.*;
import org.javatraining.model.conversion.CourseConverter;
import org.javatraining.model.conversion.CoursePersonStatusConverter;
import org.javatraining.model.conversion.NewsConverter;
import org.javatraining.model.conversion.PersonConverter;
import org.javatraining.service.CourseService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by olga on 07.06.15.
 */
@Stateless
public class CourseServiceImpl implements CourseService {

    @EJB
    private CourseDAO courseDAO;

    @EJB
    private CoursePersonStatusDAO coursePersonStatusDAO;

    @EJB
    private NewsDAO newsDAO;

    @EJB
    private PersonDAO personDAO;

    @EJB
    private LessonDAO lessonDAO;

    @Override
    public CourseVO saveCourse(@NotNull @Valid CourseVO courseVO) {
        CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
        courseVO.setId(courseDAO.save(courseEntity).getId());
        return courseVO;
    }

    @Override
    public void clear() {
        courseDAO.clear();
    }

    @Override
    public CourseVO updateCourse(@NotNull @Valid CourseVO courseVO) {
        courseVO.setId(courseDAO
                .update(CourseConverter.convertVOToEntity(courseVO))
                .getId());
        return courseVO;
    }

    @Override
    public void save(@NotNull @Valid CourseWithDetailsVO courseVO) {
        CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
        courseDAO.save(courseEntity);
        courseVO.setId(courseEntity.getId());
        for (PersonVO teacherVO : courseVO.getTeachers()) {
            CoursePersonStatusVO statusVO =
                    new CoursePersonStatusVO(CourseStatus.SIGNED, courseVO.getId(), teacherVO.getId());
            CoursePersonStatusEntity statusEntity =
                    CoursePersonStatusConverter.convertVOToEntityWithoutEntityRelations(statusVO);
            PersonEntity personEntity = personDAO.getById(teacherVO.getId());
            statusEntity.setPerson(personEntity);
            statusEntity.setCourse(courseEntity);
            coursePersonStatusDAO.save(statusEntity);
        }
    }

    @Override
    public void createFromPrototype(@NotNull @Valid CourseWithDetailsVO courseWithDetailsVO, @NotNull CourseVO coursePrototype) {
        save(courseWithDetailsVO);
        CourseEntity courseEntity = courseDAO.getById(courseWithDetailsVO.getId());
        List<LessonEntity> lessonsFromPrototype = lessonDAO.getByCourseId(coursePrototype.getId());
        for (LessonEntity lesson : lessonsFromPrototype) {
            LessonEntity lessonEntity = new LessonEntity(lesson);
            lessonEntity.setCourse(courseEntity);
            lessonDAO.save(lessonEntity);
            // TODO: for every lesson do practices saving
        }
    }

    @Override
    public List<CourseVO> getAllStartedAfterDate(Date date) {
        List<CourseEntity> courseEntities = courseDAO.getAllCoursesStartedAfterDate(new java.sql.Date(date.getTime()));
        Set<CourseVO> courseVOs = CourseConverter.convertEntitiesToVOs(courseEntities);
        return new ArrayList<>(courseVOs);
    }

    @Override
    public List<CourseVO> getAllEndedBeforeDate(Date date) {
        List<CourseEntity> courseEntities = courseDAO.getAllCoursesEndedBeforeDate(new java.sql.Date(date.getTime()));
        Set<CourseVO> courseVOs = CourseConverter.convertEntitiesToVOs(courseEntities);
        return new ArrayList<>(courseVOs);
    }

    @Override
    public CourseVO removeCourse(@NotNull CourseVO courseVO) {
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        newsDAO.removeById(courseEntity.getNews().iterator().next().getId());
        courseDAO.removeById(courseEntity.getId());
        return courseVO;
    }

    @Override
    public List<CourseVO> getAll() {
        return CourseConverter.convertEntitiesToVOs(courseDAO.getAllCourses())
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public CourseVO getCourseById(@NotNull Long id) {
        return CourseConverter.convertEntityToVO(courseDAO.getById(id));
    }

    @Override
    public CourseVO addPersonsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity
                .getCoursePersonEntities()
                .stream()
                .map(CoursePersonStatusEntity::getPerson)
                .collect(Collectors.toList())
                .add(PersonConverter
                        .convertVOsToEntities(persons)
                        .iterator().next());
        courseDAO.update(courseEntity);
        return courseVO;
    }

    @Override
    public CourseVO removePersonsFromCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        courseEntity
                .getCoursePersonEntities()
                .stream()
                .map(CoursePersonStatusEntity::getPerson)
                .collect(Collectors.toList())
                .remove(PersonConverter
                        .convertVOsToEntities(persons)
                        .iterator().next());
        courseDAO.update(courseEntity);
        return courseVO;
    }

    @Override
    public List<PersonVO> getAllPersonsFromCourseByRole(@NotNull CourseVO courseVO, @NotNull PersonRole role) {
        return PersonConverter.convertEntitiesToVOs(courseDAO.getById(courseVO.getId())
                .getCoursePersonEntities()
                .stream()
                .filter(coursePersonStatus -> coursePersonStatus.getPerson()
                        .getPersonRole() == role)
                .map(CoursePersonStatusEntity::getPerson)
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public NewsVO addNewsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid NewsVO newsVO) {
        CourseEntity courseEntity = courseDAO.getById(courseVO.getId());
        NewsEntity newsEntity = NewsConverter.convertVOToEntity(newsVO);
        courseEntity.getNews().add(newsEntity);
        courseDAO.update(courseEntity);
        return newsVO;
    }

    @Override
    public NewsVO removeNewsFromCourse(@NotNull CourseVO courseVO, @NotNull NewsVO newsVO) {
        CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
        courseEntity.getNews().remove(NewsConverter.convertVOToEntity(newsVO));
        courseDAO.update(courseEntity);
        return newsVO;
    }

    @Override
    public NewsVO updateNews(@NotNull @Valid NewsVO newsVO) {
        NewsEntity newsEntity = newsDAO.getById(newsVO.getId());
        newsEntity.setTitle(newsVO.getTitle());
        newsEntity.setDate(newsVO.getDate());
        newsEntity.setDescription(newsVO.getContent());
        newsDAO.update(newsEntity);
        return NewsConverter.convertEntityToVO(newsEntity);

    }

    @Override
    public NewsVO getAllNewsById(@NotNull Long id) {
        return NewsConverter
                .convertEntityToVO(newsDAO.getById(id));
    }


    @Override
    public NewsVO getNewsByIdFromCourse(@NotNull Long courseId,@NotNull Long newsId) {

      List <NewsEntity> newsEntities= courseDAO
              .getById(courseId)
                .getNews()
                .stream()
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.toList());
                     return  NewsConverter.convertEntityToVO(newsEntities.get(0));
    }
    @Override
    public List<NewsVO> getAllNewsFromCourse(@NotNull CourseVO courseVO) {

        return NewsConverter.convertEntitiesToVOs(courseDAO.getById(courseVO.getId())
                .getNews()
                .stream()
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
    }


    public List<NewsVO> getAllNews() {
        return NewsConverter
                .convertEntitiesToVOs(newsDAO.getAllNews())
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsVO> getNewsByCourseId(@NotNull Long courseId){
        return NewsConverter.convertEntitiesToVOs(courseDAO.getById(courseId)
                .getNews()
                .stream()
                .collect(Collectors.toList()))
                .stream()
                .collect(Collectors.toList());
    }

}