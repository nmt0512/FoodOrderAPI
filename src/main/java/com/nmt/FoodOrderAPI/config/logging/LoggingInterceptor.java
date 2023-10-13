package com.nmt.FoodOrderAPI.config.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

@Component
public class LoggingInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        StringBuilder paramsStringBuilder = new StringBuilder();
        Enumeration<String> paramsName = request.getParameterNames();
        while (paramsName.hasMoreElements()) {
            String paramName = paramsName.nextElement();
            paramsStringBuilder.append(paramName);
            paramsStringBuilder.append("=");
            paramsStringBuilder.append(request.getParameterValues(paramName)[0]);
            paramsStringBuilder.append("&");
        }
        int lastIndexOfAmpersand = paramsStringBuilder.lastIndexOf("&");
        if (lastIndexOfAmpersand >= 0)
            paramsStringBuilder.deleteCharAt(lastIndexOfAmpersand);
        else
            paramsStringBuilder.append("null");

        LOGGER.info(
                "REQUEST: {} | PARAMS: {} >>> RESPONSE STATUS: {}",
                request.getRequestURI(),
                paramsStringBuilder,
                response.getStatus()
        );
    }
}
