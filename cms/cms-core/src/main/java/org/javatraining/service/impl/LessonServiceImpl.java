package org.javatraining.service.impl;

import org.javatraining.dao.LessonDAO;
import org.javatraining.entity.LessonEntity;
import org.javatraining.model.LessonVO;
import org.javatraining.service.LessonService;

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.Set;

import static org.javatraining.model.conversion.LessonConverter.*;

/**
 * Created by asudak on 6/24/15.
 */
@Stateless
public class LessonServiceImpl implements LessonService {
    @EJB
    private LessonDAO lessonDAO;

    @Override
    public void save(@NotNull @Valid LessonVO lessonVO) {
        LessonEntity lessonEntity = convertVOToEntity(lessonVO);
        lessonDAO.save(lessonEntity);
        lessonVO.setId(lessonEntity.getId());
    }

    @Override
    public LessonVO update(@NotNull @Valid LessonVO lessonVO) {
        return convertEntityToVO(lessonDAO.update(convertVOToEntity(lessonVO)));
    }

    @Override
    public void remove(@NotNull LessonVO lessonVO) {
        lessonDAO.remove(convertVOToEntity(lessonVO));
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

    @Nullable
    @Override
    public void deleteByOrderNum(@NotNull Long courseId, @NotNull Long orderNum) {
        lessonDAO.removeByOrderNum(courseId, orderNum);
    }
}
