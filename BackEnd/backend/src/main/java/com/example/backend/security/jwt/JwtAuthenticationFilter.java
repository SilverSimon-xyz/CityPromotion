package com.example.backend.security.jwt;

import com.example.backend.dto.Account;
import com.example.backend.security.services.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

// Execute Before Executing Spring Security Filters
// Validate the JWT Token and Provides user details to Spring Security for Authentication
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

            //Get JWT token from HTTP request
            String header = request.getHeader("Authorization");

            if(header == null || !header.startsWith("Bearer ")) {
               filterChain.doFilter(request, response);
                return;
            }

            try {
                String token = header.substring(7);
                String username = jwtService.extractUsername(token);
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                //Validate Token
                if(username != null && authentication == null) {
                    //get username from token
                    Account account = accountService.loadUserByUsername(username);

                    if (jwtService.validateToken(token, account)) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                account,
                                null,
                                account.getAuthorities()
                        );

                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                filterChain.doFilter(request, response);

            } catch(Exception e) {
                handlerExceptionResolver.resolveException(request, response, null, e);
            }


    }

}
