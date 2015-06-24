package org.javatraining.model.conversion;

import org.javatraining.entity.LessonEntity;
import org.javatraining.model.LessonVO;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by asudak on 6/24/15.
 */
public class LessonConverter {
    public static LessonVO convertEntityToVO(LessonEntity lessonEntity) {
        LessonVO lessonVO = new LessonVO();
        lessonVO.setId(lessonEntity.getId());
        lessonVO.setCreateDate(lessonEntity.getCreateDate());
        lessonVO.setDescription(lessonEntity.getDescription());
        lessonVO.setTopic(lessonEntity.getTopic());
        lessonVO.setOrderNum(lessonEntity.getOrderNum());
        return lessonVO;
    }

    public static LessonEntity convertVOToEntity(LessonVO lessonVO) {
        LessonEntity lessonEntity = new LessonEntity();
        lessonEntity.setId(lessonVO.getId());
        lessonEntity.setCreateDate(lessonVO.getCreateDate());
        lessonEntity.setDescription(lessonVO.getDescription());
        lessonEntity.setTopic(lessonVO.getTopic());
        lessonEntity.setOrderNum(lessonVO.getOrderNum());
        return lessonEntity;
    }

    public static Set<LessonVO> convertEntitiesToVOs(@NotNull Collection<LessonEntity> lessonEntities) {
        Set<LessonVO> lessonVOs = new HashSet<>();
        for(LessonEntity lessonEntity : lessonEntities) {
            lessonVOs.add(convertEntityToVO(lessonEntity));
        }
        return lessonVOs;
    }

    public static Set<LessonEntity> convertVOsToEntities(@NotNull Collection<LessonVO> lessonVOs) {
        Set<LessonEntity> lessonEntities = new HashSet<>();
        for(LessonVO LessonVOs : lessonVOs) {
            lessonEntities.add(convertVOToEntity(LessonVOs));
        }
        return lessonEntities;
    }
}
