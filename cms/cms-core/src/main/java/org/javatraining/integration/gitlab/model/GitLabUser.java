package org.javatraining.integration.gitlab.model;

import java.net.URL;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 14:33.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabUser {
    private String email;//required
    private String password;//required
    private String userName;//required
    private String name;//required
    private String skype;//required
    private String linkedIn;
    private String twitterAccount;
    private URL websiteUrl;
    private int projectsLimit;
    private String externUID;
    private String provider;
    private String bio;
    private boolean admin;
    private boolean canCreateGroup;
    private boolean confirm;

    public GitLabUser(){

    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getTwitterAccount() {
        return twitterAccount;
    }

    public void setTwitterAccount(String twitterAccount) {
        this.twitterAccount = twitterAccount;
    }

    public URL getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(URL websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public int getProjectsLimit() {
        return projectsLimit;
    }

    public void setProjectsLimit(int projectsLimit) {
        this.projectsLimit = projectsLimit;
    }

    public String getExternUID() {
        return externUID;
    }

    public void setExternUID(String externUID) {
        this.externUID = externUID;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isCanCreateGroup() {
        return canCreateGroup;
    }

    public void setCanCreateGroup(boolean canCreateGroup) {
        this.canCreateGroup = canCreateGroup;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }
}
