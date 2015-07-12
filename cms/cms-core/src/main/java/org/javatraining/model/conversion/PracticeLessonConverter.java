package org.javatraining.model.conversion;

import org.javatraining.entity.PracticeLessonEntity;
import org.javatraining.model.PracticeLessonVO;

/**
 * Created by olga on 18.06.15.
 */
public class PracticeLessonConverter {
    public static PracticeLessonVO convertEntityToVO(PracticeLessonEntity entity) {
        return new PracticeLessonVO(entity.getId(), entity.getTask(), entity.getOrderNum());
    }

    public static PracticeLessonEntity convertVOToEntity(PracticeLessonVO lessonVO) {
        PracticeLessonEntity lessonEntity = new PracticeLessonEntity(lessonVO.getId(), lessonVO.getTask(), lessonVO.getOrderNum());
        return lessonEntity;
    }
}
