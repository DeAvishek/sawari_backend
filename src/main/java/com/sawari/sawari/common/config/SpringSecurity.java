package com.sawari.sawari.common.config;

import com.sawari.sawari.common.filter.JwtAuthFilter;
import com.sawari.sawari.forRider.service.genral.UserDetailsMpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//this permit all paths are for texting
@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Autowired
    private UserDetailsMpl userDetailsMpl;

    @Autowired
    private JwtAuthFilter authFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.
                 csrf(csrf->csrf.disable())
                .authorizeHttpRequests(
                        request->request
                                .requestMatchers("/Rider/create","/Rider/verify/**","/Driver/create","/Driver/verify/**","/ws/**",
                                        "/get/autocomplete/**","/get/src_dest/direction","/Driver/login","/Driver/refresh-token/**","/Rider/login","/Rider/refresh-token/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .headers(headers->headers.frameOptions(frame->frame.disable()))
                .build();

    }
    //in security allowing many url without access just for checking
    //ignoring the websocket
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/trip/**");
//    }
    //just basic password encrypter this
    @Bean
    public PasswordEncoder passwordEncoder() {
        return org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance();
    }

}
