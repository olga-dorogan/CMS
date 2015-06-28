package org.javatraining.model;

/**
 * The project name is cms.
 * Created by sergey on 28.06.15 at 17:26.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class PersonDescriptionVO {
    private Long id;
    private Image personalLogo;
    private String graduation;
    private String experience;
    private String phoneNubmer;

    public PersonDescriptionVO() {

    }

    public PersonDescriptionVO(Long id, Image personalLogo, String graduation, String experience, String phoneNubmer) {
        this.id = id;
        this.personalLogo = personalLogo;
        this.graduation = graduation;
        this.experience = experience;
        this.phoneNubmer = phoneNubmer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Image getPersonalLogo() {
        return personalLogo;
    }

    public void setPersonalLogo(Image personalLogo) {
        this.personalLogo = personalLogo;
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

    public String getPhoneNubmer() {
        return phoneNubmer;
    }

    public void setPhoneNubmer(String phoneNubmer) {
        this.phoneNubmer = phoneNubmer;
    }
}
