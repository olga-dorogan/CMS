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

    PersonVO savePerson(@NotNull @Valid PersonVO personVO);

    PersonVO updatePerson(@NotNull @Valid PersonVO personVO);

    void removePerson(@NotNull PersonVO personVO);

    @Valid
    PersonVO getPersonById(@NotNull Long id);

    PersonVO fillPersonCourses(@NotNull @Valid PersonVO personVO);

    PersonVO fillPersonMarks(@NotNull @Valid PersonVO personVO);

    PersonVO fillPersonForumMessages(@NotNull @Valid PersonVO personVO);

    //    Person --- PersonRole methods

    @Valid
    List<PersonVO> getPersonsByRole(@NotNull @Valid PersonRoleVO role);

    //     Person --- Course methods

    void addPersonToCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    void removePersonFromCourse(@NotNull PersonVO personVO, @NotNull CourseVO courseVO);

    @NotNull
    @Valid
    List<CourseVO> getPersonCourses(@NotNull PersonVO personVO);

    //    Person --- Mark methods

    void setPersonMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO);

    void updatePersonMark(@NotNull PersonVO personVO, @NotNull MarkVO markVO);

    @NotNull
    @Valid
    List<MarkVO> getPersonMarks(@NotNull PersonVO personVO);
}
