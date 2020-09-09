package com.melaniepiotrowski.mail;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MailAdapter implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent = new APIGatewayProxyResponseEvent();

        try {
            String requestString = apiGatewayProxyRequestEvent.getBody();

            JSONParser parser = new JSONParser();
            JSONObject requestJsonObject = (JSONObject) parser.parse(requestString);

            String requestMessage = null;
            String responseMessage = null;

            if (requestJsonObject != null) {
                if (requestJsonObject.get("requestMessage") != null) {
                    requestMessage = requestJsonObject.get("requestMessage").toString();
                }
            }

            Map<String, String> responseBody = new HashMap<String, String>();
            responseBody.put("responseMessage", requestMessage);
            responseMessage = new JSONObject(responseBody).toJSONString();
            generateResponse(apiGatewayProxyResponseEvent, responseMessage);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return apiGatewayProxyResponseEvent;
    }
    
    private void generateResponse(APIGatewayProxyResponseEvent apiGatewayProxyResponseEvent, String requestMessage) {
        apiGatewayProxyResponseEvent.setHeaders(Collections.singletonMap("timeStamp", String.valueOf(System.currentTimeMillis())));
        apiGatewayProxyResponseEvent.setStatusCode(200);
        apiGatewayProxyResponseEvent.setBody(requestMessage);

    }
}