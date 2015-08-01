package org.javatraining.integration.gitlab.impl;

import org.apache.velocity.app.VelocityEngine;
import org.javatraining.integration.gitlab.api.model.GitLabUser;
import org.javatraining.notification.email.impl.MailNotification;
import org.javatraining.notification.email.interfaces.NotificationService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The project name is cms.
 * Created by sergey on 17.06.15 at 13:26.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabNotificationServiceImpl implements NotificationService<GitLabUser> {
    @Override
    public void sendNotificationToEndPoint(String subject, String message, GitLabUser user) {
        Properties props = new Properties();
        JSONParser parser = new JSONParser();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("mail/mail_properties");
            JSONObject propertiesMarshaler = (JSONObject) parser.parse(new InputStreamReader(is));

            props.put("mail.transport.protocol", propertiesMarshaler.get("mail.transport.protocol"));
            props.put("mail.host", propertiesMarshaler.get("mail.host"));
            props.put("mail.smtp.auth", propertiesMarshaler.get("mail.smtp.auth"));
            props.put("mail.smtp.port", propertiesMarshaler.get("mail.smtp.port"));
            props.put("mail.debug", propertiesMarshaler.get("mail.debug"));
            props.put("mail.smtp.socketFactory.port", propertiesMarshaler.get("mail.smtp.socketFactory.port"));
            props.put("mail.smtp.socketFactory.class", propertiesMarshaler.get("mail.smtp.socketFactory.class"));
            props.put("mail.smtp.socketFactory.fallback", propertiesMarshaler.get("mail.smtp.socketFactory.fallback"));

            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("mail/account_properties");
            propertiesMarshaler = (JSONObject) parser.parse(new InputStreamReader(resourceAsStream));

            String from = (String) propertiesMarshaler.get("email_for_gitlab_notification");
            String password = (String) propertiesMarshaler.get("password_for_gitlab_notification");

            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    });

            Transport transport = session.getTransport();
            InternetAddress addressFrom = new InternetAddress(from);

            VelocityEngine velocityEngine = MailNotification.createEngine();

            MimeMessage mMessage = new MimeMessage(session);
            mMessage.setSender(addressFrom);
            mMessage.setSubject(subject);

            Map<String, String> model = new HashMap<>();
            model.put("username", user.getName());
            model.put("password", user.getPassword());
            mMessage.setContent(VelocityEngineUtils
                    .mergeTemplateIntoString(velocityEngine, "velocity/gitlab-mail-template.vm", "UTF-8", model),
                    "text/html; charset=UTF-8");

            mMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

            transport.connect();
            Transport.send(mMessage);
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
