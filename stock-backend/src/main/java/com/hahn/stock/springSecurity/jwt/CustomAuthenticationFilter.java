package com.hahn.stock.springSecurity.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hahn.stock.springSecurity.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final DaoAuthenticationProvider authenticationManager;
    private final JwtService jwtService;

    public CustomAuthenticationFilter(DaoAuthenticationProvider authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
            );
            return authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user, request.getRequestURI());
        this.responseForCorrectCredentials(response, token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        this.responseForWrongCredentials(response);
    }


    @Override
    @Operation(summary = "Login", description = "Authenticate and obtain an access token")
    protected String obtainUsername(HttpServletRequest request) {
        // Retrieve username from request
        return super.obtainUsername(request);
    }

    @Override
    @Operation(summary = "Login", description = "Authenticate and obtain an access token")
    protected String obtainPassword(HttpServletRequest request) {
        // Retrieve password from request
        return super.obtainPassword(request);
    }


    private void responseForCorrectCredentials(HttpServletResponse response, String token) throws IOException {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("authToken", token);
        responseBody.put("expiresIn", String.valueOf(this.jwtService.getTokenExpirationDate()));
        response.setStatus(200);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.addHeader(this.jwtService.getAuthorizationHeader(), jwtService.getTokenPrefix() + token);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }

    private void responseForWrongCredentials(HttpServletResponse response) throws IOException {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Wrong Credentials");
        response.setStatus(400);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseBody));
    }


}
