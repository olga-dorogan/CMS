package org.javatraining.service.impl;

import org.javatraining.dao.CourseDAO;
import org.javatraining.dao.CoursePersonStatusDAO;
import org.javatraining.dao.NewsDAO;
import org.javatraining.entity.CourseEntity;
import org.javatraining.entity.NewsEntity;
import org.javatraining.entity.PersonEntity;
import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.CourseVO;
import org.javatraining.model.NewsVO;
import org.javatraining.model.PersonVO;
import org.javatraining.model.conversion.CourseConverter;
import org.javatraining.model.conversion.NewsConverter;
import org.javatraining.model.conversion.PersonConverter;
import org.javatraining.service.CourseService;
import org.javatraining.service.exception.UnsupportedOperationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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

    @Override
    public CourseVO save(@NotNull @Valid CourseVO courseVO) {
        CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
        courseVO.setId(courseDAO.save(courseEntity).getId());
        return courseVO;
    }

    @Override
    public void clear() {
        courseDAO.clear();
    }

    @Override
    public CourseVO update(@NotNull @Valid CourseVO courseVO) {
        courseVO.setId(courseDAO
                .update(CourseConverter
                        .convertVOToEntity(courseVO))
                .getId());
        return courseVO;
    }

    @Override
    public CourseVO remove(@NotNull CourseVO courseVO) {
        courseDAO.removeById(courseVO.getId());
         return courseVO;
    }

    @Override
    public List<CourseVO> getAll() {
        return CourseConverter.convertEntitiesToVOs(courseDAO.getAllCourses()).
                stream()
                .collect(Collectors.toList());
    }

    @Override
    public CourseVO getCourseById(@NotNull Long id) {
        return CourseConverter.convertEntityToVO(courseDAO.getById(id));
    }

    @Override
    public CourseVO addPersonsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {

//        courseEntity.setPersons(PersonConverter.convertVOsToEntities(persons));
        return courseVO;
    }

    @Override
    public CourseVO removePersonsFromCourse(@NotNull CourseVO courseVO, @NotNull @Valid List<PersonVO> persons) {
           CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
        Set<PersonEntity> personEntities = PersonConverter.convertVOsToEntities(persons);

//        courseEntity.getPersons().remove(personEntities);
        return CourseConverter.convertEntityToVO(courseDAO.update(courseEntity));

    }

    @Override
    public List<PersonVO> getAllPersonsFromCourseByRole(@NotNull CourseVO courseVO, @NotNull PersonRole role) {
        CourseEntity courseEntity =CourseConverter.convertVOToEntity(courseVO);
       //                stream().filter(person -> person.getPersonRole() == role)
       // .collect(Collectors.toList());
        throw new UnsupportedOperationException();
    }

    @Override
    public NewsVO addNewsToCourse(@NotNull CourseVO courseVO, @NotNull @Valid NewsVO newsVO) {
        CourseEntity courseEntity = CourseConverter.convertVOToEntity(courseVO);
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
        NewsEntity newsEntity =  newsDAO.getById(newsVO.getId());
        newsEntity.setTitle(newsVO.getTitle());
        newsEntity.setDate(newsVO.getDate());
        newsEntity.setDescription(newsVO.getContent());
        newsDAO.update(newsEntity);
       return NewsConverter.convertEntityToVO(newsEntity);

    }

    @Override
    public NewsVO getNewsById(@NotNull Long id) {
        return NewsConverter
                .convertEntityToVO(newsDAO.getById(id));
    }

    @Override
    public List<NewsVO> getAllNewsFromCourse(@NotNull CourseVO courseVO) {
        return NewsConverter.convertEntitiesToVOs(CourseConverter
                .convertVOToEntity(courseVO)
                .getNews())
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsVO> getAllNews() {
        return NewsConverter
                .convertEntitiesToVOs(newsDAO.getAllNews())
                .stream()
                .collect(Collectors.toList());
    }
}