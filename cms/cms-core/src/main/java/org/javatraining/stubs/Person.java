package org.javatraining.stubs;

/**
 * Created by asudak on 5/29/15.
 */

import flexjson.JSON;

import javax.validation.constraints.NotNull;

/**
 * Stub for Person value object class.
 */
public class Person {
    @JSON(include = false)
    private int id;
    private String clientId;
    @NotNull
    private String firstname;
    private String secondname;
    private String lastname;
    private String role;

    public Person() {

    }

    public Person(String clientId) {
        setClientId(clientId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSecondname() {
        return secondname;
    }

    public void setSecondname(String secondname) {
        this.secondname = secondname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
