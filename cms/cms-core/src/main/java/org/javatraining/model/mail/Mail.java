package org.javatraining.model.mail;

import org.javatraining.model.PersonVO;

/**
 * The project name is cms.
 * Created by sergey on 07.07.15 at 17:26.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class Mail {
    private MailType type;
    private String memory;
    private PersonVO userTo;

    public Mail() {

    }

    public Mail(MailType type, String memory, PersonVO userTo) {
        this.setType(type);
        this.setMemory(memory);
        this.setUserTo(userTo);
    }

    public MailType getType() {
        return type;
    }

    public void setType(MailType type) {
        this.type = type;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public PersonVO getUserTo() {
        return userTo;
    }

    public void setUserTo(PersonVO userTo) {
        this.userTo = userTo;
    }
}

