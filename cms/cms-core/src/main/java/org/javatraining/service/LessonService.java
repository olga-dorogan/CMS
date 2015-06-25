package org.javatraining.service;

import org.javatraining.model.LessonVO;

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

    @Valid
    @Nullable
    LessonVO getById(@NotNull Long id);

    @Nullable
    @Valid
    Set<LessonVO> getByCourseId(@NotNull Long courseId);

    @Nullable
    @Valid
    LessonVO getByOrderNum(@NotNull Long courseId, @NotNull Long orderNum);
}
