package org.javatraining.integration.gitlab.exception;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 15:02.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(){

    }

    public UserNotFoundException(String message){
        super(message);
    }
}
