package com.blogging.security;

import com.blogging.exceptions.JwtTokenException;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.ResponseApi;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailService userDetailService;

    @Autowired
    private JwtManager jwtManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader("Authorization");

            String username = null;
            String jwt = null;


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = this.jwtManager.extractUsername(jwt);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailService.loadUserByUsername(username);

                    if (this.jwtManager.validateToken(jwt, userDetails))   {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                    else
                    {
                        try {
                            throw new JwtTokenException("Invalid JWT Token.");
                        }
                        catch (JwtTokenException ex)
                        {
                            String requestURL = request.getRequestURL().toString();
                            if(!(requestURL.contains("register") || requestURL.contains("login") || requestURL.contains("api-docs")))
                            request.setAttribute("Exception" , ex);
                        }
                    }
                }
                else
                {
                    try {
                        throw new JwtTokenException("JWT token does not contain subject or User already Logged In.");
                    }
                    catch (JwtTokenException ex)
                    {
                        String requestURL = request.getRequestURL().toString();
                        if(!(requestURL.contains("register") || requestURL.contains("login") || requestURL.contains("api-docs")))
                        request.setAttribute("Exception" , ex);
                    }
                }
            }
            else
            {
                try {
                    throw new JwtTokenException("JWT token does not start with Bearer.");
                }
                catch (JwtTokenException ex)
                {
                    String requestURL = request.getRequestURL().toString();
                    if(!(requestURL.contains("register") || requestURL.contains("swagger-ui") || requestURL.contains("login") || requestURL.contains("api-docs")))
                    request.setAttribute("Exception" , ex);
                }

            }

        }catch (ExpiredJwtException ex)
        {
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestURL = request.getRequestURL().toString();
            // allow for Refresh Token creation if following conditions are true.
            if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else
                request.setAttribute("Exception", ex);
        }
        catch (BadCredentialsException ex)
        {
            request.setAttribute("Exception" , ex);
        }

        filterChain.doFilter(request,response);
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

        Set<Integer> ss = new HashSet<>();

        // create a UsernamePasswordAuthenticationToken with null values.
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                null, null, null);
        // After setting the Authentication in the context, we specify
        // that the current user is authenticated. So it passes the
        // Spring Security Configurations successfully.
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        // Set the claims so that in controller we will be using it to create
        // new JWT
        request.setAttribute("claims", ex.getClaims());
    }


}
