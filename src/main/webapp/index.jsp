<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-US">
<head>
    <title>Melanie Piotrowski | Web Portfolio</title>

    <meta name="author" content="Melanie Piotrowski">
    <meta name="description" content="This portfolio showcases my knowledge and skills as a web developer.">
    <meta charset="utf-8">

    <link rel="stylesheet" type="text/css" href="./css/style.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Pacifico&display=swap">
    <link rel="shortcut icon" type="image/x-icon" href="./images/favicon.ico">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" src="./js/contactForm.js"></script>
    <script src="https://www.google.com/recaptcha/api.js?render=6Lf0SsgZAAAAANKdy6bJsI0Mxk79XPPI5em_t46S" async defer></script>
</head>
<body>
    <main>
        <h1>Melanie Piotrowski</h1>
        <span id="icons">
            <a class="fa fa-fw fa-4x spacing" id="resume" title="Download a copy of my resume (PDF)" href="https://drive.google.com/file/d/1qxJTmAdzAAvwVRnAzlmVZn-Yafnz3Lf3/view?usp=sharing">
                <span class="fa fa-fw fa-file-pdf-o" aria-hidden="true"></span>
                <span class="sr-only">Download a copy of my resume (PDF)</span>
            </a>
            <a class="fa fa-fw fa-4x fa-linkedin spacing" title="View my LinkedIn profile" href="https://www.linkedin.com/in/melanie-piotrowski/"></a>
            <a class="fa fa-fw fa-4x fa-github spacing" title="View my GitHub profile" href="https://github.com/my-name-melanie"></a>
            <a id="email" class="fa fa-fw fa-4x fa-envelope spacing" title="Send me an E-Mail"></a>
        </span>
        <%@ include file="mailForm.html" %>
    </main>
</body>
</html>