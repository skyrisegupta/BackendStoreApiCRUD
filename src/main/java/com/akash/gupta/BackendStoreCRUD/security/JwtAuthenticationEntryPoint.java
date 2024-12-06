package com.akash.gupta.BackendStoreCRUD.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

//so that we can autowire anywhere
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //this method will run for Calling authorized api we'll get unauthorized request or we can
   // say unauthenticated request
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                                          unauthorized request 401
        PrintWriter writer = response.getWriter();
        writer.println("Access Denied  !!" + authException.getMessage());

    }
}
