package org.javatraining.service;

import org.javatraining.model.CourseVO;
import org.javatraining.model.MarkVO;
import org.javatraining.model.PersonRoleVO;
import org.javatraining.model.PersonVO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by olga on 07.06.15.
 */
public interface PersonService {

    //     Person methods

    PersonVO save(@NotNull @Valid PersonVO personVO);

    PersonVO update(@NotNull @Valid PersonVO personVO);

    void remove(@NotNull PersonVO personVO);

    @Valid
    PersonVO getById(@NotNull Long id);

    //    Person --- PersonRole methods

    @Valid
    List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRoleVO role);

    //     Person --- Course methods

    void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    @NotNull
    @Valid
    List<CourseVO> getCourses(@NotNull PersonVO personVO);

    //    Person --- Mark methods

    void setMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO);

    void updateMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO);

    @NotNull
    @Valid
    List<MarkVO> getMarks(@NotNull PersonVO personVO);
}
