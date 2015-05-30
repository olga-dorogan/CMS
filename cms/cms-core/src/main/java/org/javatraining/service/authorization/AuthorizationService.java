package org.javatraining.service.authorization;

import org.javatraining.integration.google.oauth.impl.GoogleUserinfoServiceImpl;

/**
 * Created by olga on 29.05.15.
 */
// TODO: реализовать методы AuthorizationService
public interface AuthorizationService {
    enum Role {UNAUTHORIZED, TEACHER, STUDENT}

    //        обратиться к БД для определения роли;
//        если clientId не зарегистрирован в системе,
//        вернуть Role.UNAUTHORIZED
    Role getRoleByClientId(String clientId);

    // TODO: изменить тип параметра на тип соответствующего value object
//    сохранение информации о пользователе в БД
    void registerUser(GoogleUserinfoServiceImpl.Userinfo userinfo);
}
