package org.javatraining.integration.google.oauth.impl;

import org.javatraining.integration.google.oauth.GoogleUserinfoService;
import org.javatraining.integration.google.oauth.exception.AuthException;
import org.javatraining.integration.google.oauth.exception.GoogleConnectionAuthException;
import org.javatraining.model.PersonVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 * Created by olga on 29.05.15.
 */
@Stateless
public class GoogleUserinfoServiceImpl implements GoogleUserinfoService {
    private static final Logger log = LoggerFactory.getLogger(GoogleUserinfoServiceImpl.class);
    private static final String BASE = "https://www.googleapis.com/oauth2/v2/userinfo";

    @Override
    public String getClientIdByToken(String token) {
        final String PARAM_FIELDS_KEY = "fields";
        final String PARAM_FIELDS_VALUE = "id";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URI.create(new URL(BASE).toExternalForm()));
            target.register(Userinfo.class);
            Response response = target
                    .queryParam(PARAM_FIELDS_KEY, PARAM_FIELDS_VALUE)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                Userinfo userinfo = response.readEntity(Userinfo.class);
                log.trace("token: {}; getClientById() returns {} as clientId", token, userinfo.getId());
                return userinfo.getId();
            }
            throw new AuthException(String.format("Response from query to get client id (token = %s) returns with status code %s",
                    token, response.getStatus()));
        } catch (MalformedURLException e) {
            throw new GoogleConnectionAuthException(e);
        }
    }

    @Override
    public PersonVO getUserInfoByToken(String token) {
        final String PARAM_FIELDS_KEY = "fields";
        final String PARAM_FIELDS_VALUE = "id,email,given_name,family_name";
        try {
            Client client = ClientBuilder.newClient();
            WebTarget target = client.target(URI.create(new URL(BASE).toExternalForm()));
            target.register(Userinfo.class);
            Response response = target
                    .queryParam(PARAM_FIELDS_KEY, PARAM_FIELDS_VALUE)
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer " + token)
                    .get();

            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                Userinfo userinfo = response.readEntity(Userinfo.class);
                log.trace("token: {}; getUserInfoByToken() returns {}", token, userinfo);
                PersonVO personVO = new PersonVO();
                personVO.setEmail(userinfo.getEmail());
                personVO.setName(userinfo.getGiven_name());
                personVO.setLastName(userinfo.getFamily_name());
                //FIXME: set clientId to personVO
                return personVO;
            }
            throw new AuthException(String.format("Response from query to get user info (token = %s) returns with status code %s",
                    token, response.getStatus()));

        } catch (MalformedURLException e) {
            throw new GoogleConnectionAuthException(e);
        }
    }

    private static class Userinfo {
        private String id;
        private String email;
        private String verified_email;
        private String name;
        private String given_name;
        private String family_name;
        private String link;
        private String picture;
        private String gender;
        private String locale;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getGiven_name() {
            return given_name;
        }

        public void setGiven_name(String given_name) {
            this.given_name = given_name;
        }

        public String getFamily_name() {
            return family_name;
        }

        public void setFamily_name(String family_name) {
            this.family_name = family_name;
        }

        public String getVerified_email() {
            return verified_email;
        }

        public void setVerified_email(String verified_email) {
            this.verified_email = verified_email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        @Override
        public String toString() {
            return "Userinfo{" +
                    "id='" + id + '\'' +
                    ", email='" + email + '\'' +
                    ", given_name='" + given_name + '\'' +
                    ", family_name='" + family_name + '\'' +
                    '}';
        }
    }
}
