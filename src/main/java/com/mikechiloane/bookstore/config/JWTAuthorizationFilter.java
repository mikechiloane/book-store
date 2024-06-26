package com.mikechiloane.bookstore.config;

import com.mikechiloane.bookstore.util.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null || !accessToken.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = jwtUtil.resolveClaims(request);

            if (claims == null || !jwtUtil.validateClaims(claims)) {
                log.error("Invalid token claims");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token claims");
                return;
            }

            String username = claims.getSubject();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.error("Error authenticating user: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error authenticating user");
            return;
        }

        filterChain.doFilter(request, response);
    }

}
