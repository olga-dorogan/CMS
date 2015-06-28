package org.javatraining.service;

import org.javatraining.entity.PersonRole;
import org.javatraining.model.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * Created by olga on 07.06.15.
 */
public interface PersonService {

    //    Person Description methods
    boolean savePersonDescription(@NotNull PersonDescriptionVO personDescriptionVO);

    @Valid
    @Nullable
    PersonDescriptionVO getPersonDescription(@NotNull Long id);

    PersonDescriptionVO updatePersonDescription(@NotNull PersonDescriptionVO personDescriptionVO);

    void removePersonDescription(Long id);

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

    void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    @NotNull
    @Valid
    Set<CourseVO> getCourses(@NotNull PersonVO personVO);

    //    Person --- Mark methods

    void setMark(@NotNull PersonVO personVO, @NotNull PracticeLessonVO practiceLessonVO, @NotNull @Valid MarkVO markVO);

    void removeMark(@NotNull MarkVO markVO);

    @NotNull
    @Valid
    List<MarkVO> getMarks(@NotNull PersonVO personVO);


}
