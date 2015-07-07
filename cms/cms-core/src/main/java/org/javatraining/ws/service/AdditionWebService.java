package org.javatraining.ws.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The project name is cms.
 * Created by sergey on 07.07.15 at 17:19.
 * For more information you should send mail to codedealerb@gmail.com
 */
@Path("course")
public class AdditionWebService {

    @GET
    @Path("mailhash")
    @Produces("application/json")
    @Consumes("text/plain")
    public String getEmailHash(String email) {
        return getMDA5(email);
    }

    private String getMDA5(String email) {
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("email can't be null");
        }
        try {
            MessageDigest mDigest = MessageDigest.getInstance("MDA5");
            byte[] result = mDigest.digest(email.trim().getBytes());
            StringBuilder sb = new StringBuilder();

            for (byte aResult : result) {
                sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
