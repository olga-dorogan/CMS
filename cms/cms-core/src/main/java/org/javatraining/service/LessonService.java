package org.javatraining.service;

import org.javatraining.model.LessonVO;
import org.javatraining.model.LessonWithDetailsVO;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * Created by asudak on 6/24/15.
 */
public interface LessonService {
    void save(@NotNull @Valid LessonVO lessonVO);

    LessonVO update(@NotNull @Valid LessonVO lessonVO);

    void remove(@NotNull LessonVO lessonVO);

    void save(@NotNull @Valid LessonWithDetailsVO lessonVO);

    @Valid
    @Nullable
    LessonVO getById(@NotNull Long id);

    @Nullable
    @Valid
    Set<LessonVO> getByCourseId(@NotNull Long courseId);

    @Nullable
    @Valid
    LessonVO getByOrderNum(@NotNull Long courseId, @NotNull Long orderNum);

    @Nullable
    @Valid
    LessonWithDetailsVO getByOrderNum(@NotNull Long courseId, @NotNull Long orderNum, boolean withDetails);

    @Nullable
    @Valid
    LessonVO updateByOrderNum(@NotNull Long courseId, @NotNull Long orderNum, LessonVO lesson);

    void deleteByOrderNum(@NotNull @Valid Long courseId, @NotNull @Valid Long orderNum);
}
