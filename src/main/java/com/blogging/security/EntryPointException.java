package com.blogging.security;

import com.blogging.payloads.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;

@Component
public class EntryPointException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {


        Exception exception = (Exception) request.getAttribute("Exception");


        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String msg;


        if (exception != null)
        {
            if(exception.getCause()!=null)
            {
                msg = exception.getCause().toString()+" "+exception.getMessage();
            }
            else
            {
                msg=exception.getMessage();
            }

            byte[] body = new ObjectMapper().writeValueAsBytes(new ResponseApi(msg , false));
            response.getOutputStream().write(body);
        }
        else
        {
            if(authException.getCause()!=null)
            {
                msg = authException.getCause().toString()+" "+authException.getMessage();
            }
            else
            {
                msg=authException.getMessage();
            }

            byte[] body = new ObjectMapper().writeValueAsBytes(new ResponseApi(msg , false));
            response.getOutputStream().write(body);
        }
    }
}
