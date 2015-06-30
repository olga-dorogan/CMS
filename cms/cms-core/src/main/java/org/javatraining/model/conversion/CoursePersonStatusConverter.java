package org.javatraining.model.conversion;

import org.javatraining.entity.CoursePersonStatusEntity;
import org.javatraining.model.CoursePersonStatusVO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 30.06.15.
 */
public class CoursePersonStatusConverter {
    public static CoursePersonStatusVO convertEntityToVO(@NotNull CoursePersonStatusEntity entity) {
        CoursePersonStatusVO vo = new CoursePersonStatusVO(entity.getCourseStatus(), entity.getCourse().getId(), entity.getPerson().getId());
        return vo;
    }

    public static List<CoursePersonStatusVO> convertEntitiesToVOs(@NotNull @Valid List<CoursePersonStatusEntity> entities) {
        List<CoursePersonStatusVO> vos = new ArrayList<>(entities.size());
        for (CoursePersonStatusEntity entity : entities) {
            vos.add(convertEntityToVO(entity));
        }
        return vos;
    }
}
