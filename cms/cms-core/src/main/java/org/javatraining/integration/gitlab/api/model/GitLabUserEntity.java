package org.javatraining.integration.gitlab.api.model;


import flexjson.JSON;

import java.io.Serializable;

/**
 * The project name is cms.
 * Created by sergey on 02.06.15 at 14:33.
 * For more information you should send mail to codedealerb@gmail.com
 */
//FIXME ZAMENA JSON PROPERTY NA ANALOGI
public class GitLabUserEntity implements Serializable{
    private Integer id;
    private String email;//required
    private String password;//required
    private String username;//required
    private String name;
    private String skype;
    private String linkedIn;
    private String twitter;
    @JSON(name = "website_url")
    private String websiteUrl;
    @JSON(name = "project_limit")
    private int projectsLimit;
    @JSON(name = "extern_uid")
    private String externUID;
    private String provider;
    private String bio;
    private boolean admin;
    @JSON(name = "can_create_group")
    private boolean canCreateGroup;
    private boolean confirm;
    private boolean state;
    @JSON(name = "theme_id")
    private int themeId;
    @JSON(name = "current_sign_in_at")
    private String currentSignInAt;
    @JSON(name = "can_create_project")
    private boolean canCreateProject;
    @JSON(name = "color_scheme_id")
    private int colorSchemeId;
    @JSON(name = "avatar_url")
    private String avatarUrl;

    public GitLabUserEntity() {

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getThemeId() {
        return themeId;
    }

    public void setThemeId(int themeId) {
        this.themeId = themeId;
    }

    public String getCurrentSignInAt() {
        return currentSignInAt;
    }

    public void setCurrentSignInAt(String currentSignInAt) {
        this.currentSignInAt = currentSignInAt;
    }

    public boolean isCanCreateProject() {
        return canCreateProject;
    }

    public void setCanCreateProject(boolean canCreateProject) {
        this.canCreateProject = canCreateProject;
    }

    public int getColorSchemeId() {
        return colorSchemeId;
    }

    public void setColorSchemeId(int colorSchemeId) {
        this.colorSchemeId = colorSchemeId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
