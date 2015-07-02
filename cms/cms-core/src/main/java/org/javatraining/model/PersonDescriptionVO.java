package org.javatraining.model;

/**
 * The project name is cms.
 * Created by sergey on 28.06.15 at 17:26.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class PersonDescriptionVO extends PersonVO {
    private Image avatar;
    private String graduation;
    private String experience;
    private String phoneNumber;

    public PersonDescriptionVO() {

    }

    public PersonDescriptionVO(Long id, Image avatar, String graduation, String experience, String phoneNumber) {
        this.avatar = avatar;
        this.graduation = graduation;
        this.experience = experience;
        this.phoneNumber = phoneNumber;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getGraduation() {
        return graduation;
    }

    public void setGraduation(String graduation) {
        this.graduation = graduation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
