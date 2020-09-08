<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Melanie Piotrowski | Contact Form</title>
    <meta charset="utf-8">
</head>
<body>
    <form id="contact" action="./src/mailServlet" method="post">
        <section>
            <span class="fa fa-2x fa-envelope-o flex-header" aria-hidden="true">
                <h2>E-Mail Me!</h2>
            </span>
            <ul>
                <li>
                    <label for="name">From: <abbr title="required" aria-label="required"></abbr></label>
                    <input id="name" type="text" name="name" placeholder="Required" required>
                </li>
                <li>
                    <label for="email">E-Mail: <abbr title="required" aria-label="required"></abbr></label>
                    <input id="email" type="email" name="email" placeholder="Required" required>
                </li>
                <li>
                    <label for="subject">Subject: <abbr title="optional" aria-label="optional"></abbr></label>
                    <input id="subject" type="text" name="subject" placeholder="">
                </li>
            </ul>
        </section>
        <section>
            <label for="message">Message: <abbr title="required" aria-label="required"></abbr></label>
            <textarea id="message" name="message" required>Your message here...</textarea>
        </section>
        <button id="submit" type="submit">
            <span class="fa fa-fw fa-3x fa-spinner fa-spin"></span>
            <span class="sr-only">Sending...</span>
            Send your message
        </button>
    </form>
</body>
</html>