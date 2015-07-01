package org.javatraining.integration.gitlab.impl;

import org.apache.velocity.app.VelocityEngine;
import org.javatraining.integration.gitlab.api.model.GitLabUser;
import org.javatraining.notification.NotificationService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.InternetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * The project name is cms.
 * Created by sergey on 17.06.15 at 13:26.
 * For more information you should send mail to codedealerb@gmail.com
 */
public class GitLabNotificationServiceImpl implements NotificationService<GitLabUser> {
    private JavaMailSenderImpl sender;
    private VelocityEngine velocityEngine;

    private static VelocityEngine createEngine() throws Exception {
        VelocityEngine ve = new VelocityEngine();
        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.init(p);
        return ve;
    }

    @Override
    public void sendUserProperties(String from, GitLabUser user) {
        sender = new JavaMailSenderImpl();
        sender.setHost("smtp.gmail.com");
        sender.setUsername("codedealerb");
        sender.setPassword("");//insert pass
        try {
            velocityEngine = createEngine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(new InternetAddress(user.getEmail()));
            helper.setFrom(new InternetAddress(from));
            helper.setSentDate(new Date());
            helper.setSubject("GitLab Properties");
            Map model = new HashMap();
            model.put("gitlabProperties", user);

            String text = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, "../resources/velocity/gitlab-mail-template.html", "UTF-8", model);
            helper.setText(text, true);
        };
        sender.send(preparator);
    }
}
