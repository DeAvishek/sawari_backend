package com.sawari.sawari.common.filter;

import com.sawari.sawari.ForDriver.service.general.UserDetailsMpl;
import com.sawari.sawari.forRider.service.genral.UserDetailsMpl2;
import com.sawari.sawari.common.utiils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsMpl2 userDetailsForRider;

    @Autowired
    private UserDetailsMpl userDetailsForDriver;

    @Autowired
    private JwtUtil jwtUtil;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse reponse, FilterChain chain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");
            String jwtToken = null;
            String userName = null;
            //get the username from token and get the token from auth header
            if (authHeader != null && authHeader.startsWith("Bearer")) {
                jwtToken = authHeader.substring(7);
                userName = jwtUtil.ExtractUsername(jwtToken);
            }
            //get the user from username
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                String url = request.getRequestURI();
                UserDetails user =null;
                System.out.println("this is the url:)"+url);
                if(url.startsWith("/Rider/")){
                    user = userDetailsForRider.loadUserByUsername(userName);
                }
                if(url.startsWith("/Driver/")){
                    user = userDetailsForDriver.loadUserByUsername(userName);
                }
                //check for validate Token
                if (jwtUtil.validateToken(jwtToken, user)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }

            }
            chain.doFilter(request, reponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }

    }


}
