package org.javatraining.service.impl;

import org.javatraining.dao.LessonDAO;
import org.javatraining.dao.LessonLinkDAO;
import org.javatraining.dao.NewsDAO;
import org.javatraining.entity.LessonEntity;
import org.javatraining.entity.LessonLinkEntity;
import org.javatraining.entity.NewsEntity;
import org.javatraining.model.LessonLinkVO;
import org.javatraining.model.LessonVO;
import org.javatraining.model.LessonWithDetailsVO;
import org.javatraining.model.conversion.LessonLinkConverter;
import org.javatraining.service.LessonService;

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

import static org.javatraining.model.conversion.LessonConverter.*;

/**
 * Created by asudak on 6/24/15.
 */
@Stateless
public class LessonServiceImpl implements LessonService {

    @EJB
    private NewsDAO newsDAO;

    @EJB
    private LessonDAO lessonDAO;
    @EJB
    private LessonLinkDAO lessonLinkDAO;

    @Override
    public void save(@NotNull @Valid LessonVO lessonVO) {
        LessonEntity lessonEntity = convertVOToEntity(lessonVO);
        lessonDAO.save(lessonEntity);
        lessonVO.setId(lessonEntity.getId());
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setTitle("К курсу" + lessonEntity.getCourse().getName()
                + " была добавлена лекция");
        newsEntity.setDescription(lessonEntity.getDescription());
        newsEntity.setDate((Timestamp) lessonEntity.getCreateDate());
        newsEntity.setCourses(lessonEntity.getCourse());
        newsDAO.save(newsEntity);
    }

    @Override
    public LessonVO update(@NotNull @Valid LessonVO lessonVO) {
        return convertEntityToVO(lessonDAO.update(convertVOToEntity(lessonVO)));
    }

    @Override
    public void remove(@NotNull LessonVO lessonVO) {
        lessonDAO.remove(convertVOToEntity(lessonVO));
    }

    @Override
    public void save(@NotNull @Valid LessonWithDetailsVO lessonVO) {
        LessonEntity lessonEntity = convertVOToEntity(lessonVO);
        lessonDAO.save(lessonEntity);
        lessonVO.setId(lessonEntity.getId());
        for (LessonLinkVO linkVO : lessonVO.getLinks()) {
            linkVO.setLessonId(lessonVO.getId());
            LessonLinkEntity linkEntity = LessonLinkConverter.convertVOToEntity(linkVO);
            lessonLinkDAO.save(linkEntity);
            linkVO.setId(lessonEntity.getId());
        }
    }

    @Nullable
    @Override
    public LessonVO getById(@NotNull Long id) {
        return convertEntityToVO(lessonDAO.getById(id));
    }

    @Nullable
    @Override
    public Set<LessonVO> getByCourseId(@NotNull Long courseId) {
        return convertEntitiesToVOs(lessonDAO.getByCourseId(courseId));
    }

    @Nullable
    @Override
    public LessonVO getByOrderNum(@NotNull Long courseId, @NotNull Long orderNum) {
        return convertEntityToVO(lessonDAO.getByOrderNum(courseId, orderNum));
    }

    @Nullable
    @Override
    public LessonVO updateByOrderNum(@NotNull Long courseId, @NotNull Long orderNum, LessonVO lesson) {
        return convertEntityToVO(lessonDAO.updateByOrderNum(courseId, orderNum, convertVOToEntity(lesson)));
    }

    @Override
    public void deleteByOrderNum(@NotNull @Valid Long courseId, @NotNull @Valid Long orderNum) {
        lessonDAO.removeByOrderNum(courseId, orderNum);
    }
}
