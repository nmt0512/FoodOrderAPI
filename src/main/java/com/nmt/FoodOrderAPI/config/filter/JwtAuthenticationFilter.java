package com.nmt.FoodOrderAPI.config.filter;

import com.nmt.FoodOrderAPI.config.utils.JwtUtils;
import com.nmt.FoodOrderAPI.config.security.SecurityResponseUtils;
import com.nmt.FoodOrderAPI.enums.ResponseStatusCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        if (request.getHeader("Authorization") != null) {
            String token = request.getHeader("Authorization").substring(7);
            try {
                String username = jwtUtils.getTokenUsername(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails.getUsername(),
                                userDetails.getPassword(),
                                userDetails.getAuthorities()
                        );
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                SecurityResponseUtils.sendResponse(response, ResponseStatusCode.BAD_REQUEST, "Invalid JWT Token");
                return;
            } catch (ExpiredJwtException e) {
                SecurityResponseUtils.sendResponse(response, ResponseStatusCode.BAD_REQUEST, "JWT is expired");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}