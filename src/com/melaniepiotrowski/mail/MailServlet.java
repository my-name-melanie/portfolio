package com.melaniepiotrowski.mail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@SuppressWarnings("serial")
@WebServlet(name="MailServlet", urlPatterns={"/MailServlet"})

/*------------------------------------------
| MailerServlet class retrieves and passes |
|      passes contact form input to Mailer |
-------------------------------------------*/

public class MailServlet extends HttpServlet {
    private final String URL = "https://www.google.com/recaptcha/api/siteverify";
    private final String SECRET = "6Lf0SsgZAAAAAMzT8aSd81r-l9ludbt2NnLKiIZp";

    //validates client side captcha response
    public synchronized boolean isCaptchaValid(String secretKey, String response) {
        // return false if token is empty
        if (response == "") {
            return false;
        }

        final String PARAMS = "secret=" + secretKey + "&response=" + response;

        try { // get http object, set attributes
            HttpURLConnection http = (HttpURLConnection) new URL(URL).openConnection();
            http.setDoOutput(true);
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            // write response parameters from http object
            OutputStream out = http.getOutputStream();
            out.write(PARAMS.getBytes("UTF-8"));
            out.flush();
            out.close();

            // create stream and reader to read response
            InputStream in = http.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(in, "UTF-8"));

            // build response as string
            String inputLine = "";
            StringBuilder sb = new StringBuilder();
            while ((inputLine = rd.readLine()) != null) {
                sb.append(inputLine);
            }
            inputLine = sb.toString();

            // close stream and reader
            rd.close();
            in.close();

            // parse response
            JSONObject json = new JSONObject(inputLine);

            // return true if response indicates success and mid-range score
            return (json.getBoolean("success") && json.getDouble("score") >= .5);
        } catch (Exception e) { //validation failed, return false
            return false;
        }
    }

    // runs on form submit (POST)
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();        
        
        if (isCaptchaValid(SECRET, request.getParameter("captchatoken"))){

            // set up new Mailer with protocol & port
            Mailer javaEmail = new Mailer();
            javaEmail.setMailServerProperties();

            // get contact form input
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String message = request.getParameter("message");
            String subject = (request.getParameter("subject") != null) ? request.getParameter("subject") : "(No Subject)";

            // replace risky characters in message to display as entity tags
            String[] unsafeChars = {"\"", "#", "%", "*", "+", "<", ">", "^", "{", "}", "&"};
            String[] safeChars = {"&quot;", "&num;", "&percnt;", "&ast;", "&plus;", "&lt;", "&gt;",
                                  "&hat;", "&lcub;", "&rcub", "&amp;"};

            for (int i = 0; i < unsafeChars.length; i++) {
                name = name.replace(unsafeChars[i], safeChars[i]);
                email = email.replace(unsafeChars[i], safeChars[i]);
                subject = subject.replace(unsafeChars[i], safeChars[i]);
                message = message.replace(unsafeChars[i], safeChars[i]);
            }

            // set up PrintWriter to modify response
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setContentType("text/plain");

            try { // create new email message
                javaEmail.createEmailMessage(name, email, subject, message);
            } catch (MessagingException me) { // log error
                out.print("<span class=\"fa fa-fw fa-times invalid\" aria-hidden=\"true\"></span>Error in Creating Email");
            }
            //try { // send email and set response
            //    javaEmail.sendEmail();
                out.print("<span class=\"fa fa-fw fa-check-square-o success\" aria-hidden=\"true\"></span>Email sent Successfully!");
            //} catch (MessagingException me) { // set response to error message
            //    out.print("<span class=\"fa fa-fw fa-times invalid\" aria-hidden=\"true\"></span>Error in Sending Email");
            //}
        }else { // validation failed, set response to error message
            out.print("<span class=\"fa fa-fw fa-times invalid\" aria-hidden=\"true\"></span>Unable to Validate ReCaptcha");
        }
    }
}
