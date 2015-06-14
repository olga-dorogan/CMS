package org.javatraining.integration.gitlab.converter;

import org.javatraining.integration.gitlab.api.model.GitLabUser;
import org.javatraining.integration.gitlab.exception.UserRequiredPropertiesIsNotComparable;
import org.javatraining.model.PersonVO;

import java.util.Collection;
import java.util.TreeSet;

/**
 * The project name is cms.
 * Created by sergey on 12.06.15 at 21:21.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class PersonConverter {

    public PersonConverter() {

    }

    public GitLabUser convertPerson(PersonVO personVO) {
        GitLabUser entity = new GitLabUser();

        entity.setId(personVO.getId());
        entity.setEmail(personVO.getEmail());
        entity.setName(personVO.getName() + "_" + personVO.getLastName());

        switch (personVO.getPersonRole()) {
            case STUDENT:
                entity.setPassword("student" + personVO.getId());
                break;
            case TEACHER:
                entity.setPassword("teacher" + personVO.getId());
                break;
        }

        return entity;
    }

    public PersonVO convertGitLabUserEntity(GitLabUser userEntity) {
        PersonVO personVO = new PersonVO();

        personVO.setId(Long.valueOf(userEntity.getId()));
        personVO.setEmail(userEntity.getEmail());
        personVO.setName(userEntity.getName().split("_")[0]);//name surname
        personVO.setLastName(userEntity.getName().split("_")[1]);

        return personVO;
    }

    public PersonVO mergePersons(PersonVO p1, PersonVO p2) throws UserRequiredPropertiesIsNotComparable {
        if (!p1.getId().equals(p2.getId())) {
            throw new UserRequiredPropertiesIsNotComparable("p1 isn\'t comparable with p2");
        }
        // FIXME: changes in PersonVO
//        p1.setCourses(p2.getCourses());
//        p1.setEmail(p2.getEmail());
//        p1.setForumMessages(p2.getForumMessages());
//        p1.setLastName(p2.getLastName());
//        p1.setMarks(p2.getMarks());
        p1.setName(p2.getName());
        p1.setPersonRole(p2.getPersonRole());
        p1.setSecondName(p2.getSecondName());

        return p1;
    }

    public Collection<PersonVO> convertAllEntities(Collection<GitLabUser> entities) {
        Collection<PersonVO> personVOs = new TreeSet<>();

        for(GitLabUser x:entities){
            personVOs.add(convertGitLabUserEntity(x));
        }

        return personVOs;
    }
}
