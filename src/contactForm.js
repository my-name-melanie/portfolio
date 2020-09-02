$(function() {
    $("#email").click(function() {
    	if ($("#contact").css("display") !== "flex"){
    		$("#contact").slideDown("slow");
    		$("#contact").css("display", "flex");
        }else{
        	$("#contact").slideUp("slow");
        }
    });
});