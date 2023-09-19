package com.nmt.FoodOrderAPI.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import com.nmt.FoodOrderAPI.response.ResponseData;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityResponseUtils {
    public static void sendResponse(HttpServletResponse response, ResponseStatusCode responseStatusCode, String message)
            throws IOException {
        response.setStatus(responseStatusCode.getValue());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ObjectMapper mapper = new ObjectMapper();
        ResponseData<Object> errorResponseData = new ResponseData<>(responseStatusCode);
        errorResponseData.setMessage(message);
        String json = mapper.writeValueAsString(errorResponseData);
        response.getWriter().write(json);
    }
}
