/*----------------------
| Contact Form actions |
----------------------*/

$(function() {
    var response = "ERROR!";

    // disable further input    
    function disableForm() {
        $("input, textarea").each(function() {
            $(this).attr("disabled", true);
            setStatus($(this), "disabled");
        });       
    }

    // validate re-captcha before form submission
    function getCaptchaToken() {
        $("#status").html("<span class=\"fa fa-pulse fa-spinner\" aria-hidden=\"true\"></span>Sending...");
        
        grecaptcha.ready(function() {
            grecaptcha.execute("6Lf0SsgZAAAAANKdy6bJsI0Mxk79XPPI5em_t46S", 
                {action: 'send_email'}).then(function(token) {
                    $("#captchatoken").val(token);
                    sendEmail();
            });
        });
    }

    // send form data to servlet and return response
    function sendEmail() {
        var form = $("#contact");

        $.ajax({
            url: 'MailServlet',
            type: 'POST',
            data: form.serialize()
        }).done(function(response) {
            setResponse(response);
            disableForm();
            response.indexOf("success") != -1 
                ? setStatus($("#submit"), "success")
                : setStatus($("#submit"), "invalid");

            // auto-close contact form after 2 seconds
            setTimeout(function() {
                toggleContactForm();
            }, 2000);
        });
    }

    // clear and format invalid input
    function setErrors(current) {
        var id = current.attr("id");
        current.val("");
        setStatus(current, "invalid");

        id == "email" 
            ? current.attr("placeholder", "Invalid email format!")
            : current.attr("placeholder", "Please enter your " + id + " here!");
    }

    // set global response
    function setResponse(serverRes){
        response = serverRes;
        $("#status").html(response);
    }

    // change formatting of input
    function setStatus(input, status) {
        var id = input.attr("id");
        if (status == "default") {
          input.removeClass("invalid")
          $("label[for='" + id + "']").removeClass("invalid");
        } else {
          input.addClass(status)
          $("label[for='" + id + "']").addClass(status);
        }
    }

    // toggles visibility of contact form
    function toggleContactForm() {
        var form = $("#contact");        
        form.css("display") !== "flex"
            ? (form.slideDown("slow"), form.css("display", "flex"))
            : form.slideUp("slow");
    }

    // validate contact form input before form submission
    function validateContactForm() {
        var invalid = new Array();

        $("input, textarea").each(function() {
            var current = $(this);

            // check for invalid email address & empty inputs
            if (current.attr("required")){
                if (current.attr("id") == "email") {
                    if (!validEmail(current)) invalid.push(current);
                } else {
                    if (!current.val()) invalid.push(current);
                }
            }
        });

        return invalid;
    }

    // check entered input against RFC-5322 regex for email
    function validEmail(input){
        const pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return pattern.test(input.val());
    }

    // validate contact form, get recaptcha token, and send message (via getCaptchaToken)
    $("#contact").submit(function(event) {
        event.preventDefault();
        
        var errors = validateContactForm();
        (errors.length == 0) ? getCaptchaToken() : errors.forEach(setErrors);
    });

    // toggle visibility of contact form on Email Button click
    $("#email").click(toggleContactForm);

    // remove validation error formatting on input focus
    $("input, textarea").focus(function() {
        if (!$(this).attr("disabled")) setStatus($(this), "default");
    });
});
