package org.javatraining.integration.gitlab.converter;

import org.javatraining.integration.gitlab.api.model.GitLabUser;
import org.javatraining.integration.gitlab.exception.UserRequiredPropertiesIsNotComparable;
import org.javatraining.model.PersonVO;
import org.javatraining.service.impl.PersonServiceImpl;

import javax.inject.Inject;
import java.util.Collection;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The project name is cms.
 * Created by sergey on 12.06.15 at 21:21.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class PersonConverter {
    @Inject//FIXME @EJB + stateless?
    private PersonServiceImpl personService;

    public PersonConverter() {
    }

    public GitLabUser convertPerson(PersonVO personVO) {
        GitLabUser entity = new GitLabUser();
        //FIXME OTPRAVKA NA MILO PASS
        entity.setEmail(personVO.getEmail());
        String userName = personVO.getEmail().split("@")[0];
        entity.setUsername(userName);
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
        return personService.getByEmail(userEntity.getEmail());
    }

    public PersonVO mergePersons(PersonVO p1, PersonVO p2) throws UserRequiredPropertiesIsNotComparable {
        if (!p1.getId().equals(p2.getId())) {
            throw new UserRequiredPropertiesIsNotComparable("p1 isn\'t comparable with p2");
        }
        p1.setEmail(p2.getEmail());
        p1.setLastName(p2.getLastName());
        p1.setName(p2.getName());
        p1.setPersonRole(p2.getPersonRole());
        p1.setSecondName(p2.getSecondName());

        return p1;
    }

    public Collection<PersonVO> convertAllEntities(Collection<GitLabUser> entities) {
        Collection<PersonVO> personVOs = entities.stream().map(
                this::convertGitLabUserEntity
        ).collect(
                Collectors.toCollection(TreeSet::new)
        );

        return personVOs;
    }
}
