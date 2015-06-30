package org.javatraining.service;

import org.javatraining.entity.enums.PersonRole;
import org.javatraining.model.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
public interface PersonService {

    //     Person methods

    void saveStudent(@NotNull @Valid PersonVO personVO);

    void save(@NotNull @Valid PersonVO personVO);

    PersonVO update(@NotNull @Valid PersonVO personVO);

    void remove(@NotNull PersonVO personVO);

    @Valid
    @Nullable
    PersonVO getById(@NotNull Long id);

    @Valid
    @Nullable
    PersonVO getByEmail(@NotNull String email);

    //    Person --- PersonRole methods

    @Valid
    List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRole role);

    //     Person --- Course methods

    void addPersonRequestForCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    void removePersonRequestForCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    void approvePersonRequestForCourse(@NotNull CoursePersonStatusVO coursePersonStatusVO);

    void rejectPersonRequestForCourse(@NotNull CoursePersonStatusVO coursePersonStatusVO);

    @NotNull
    @Valid
    List<CoursePersonStatusVO> getPersonCourseStatuses(@NotNull PersonVO personVO);

    //    Person --- Mark methods

    void setMark(@NotNull PersonVO personVO, @NotNull PracticeLessonVO practiceLessonVO, @NotNull @Valid MarkVO markVO);

    void removeMark(@NotNull MarkVO markVO);

    @NotNull
    @Valid
    List<MarkVO> getMarks(@NotNull PersonVO personVO);
}
