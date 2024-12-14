package com.DoctorAppointment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)throws Exception{
        httpSecurity.csrf().disable()
                .authorizeHttpRequests((authorize)->
                        authorize.requestMatchers("/adminsys/**").permitAll()
                                .requestMatchers("/docsys/**").permitAll()
                                .requestMatchers("/clientsys/**").permitAll()
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/sys/**").permitAll()

                                .requestMatchers(HttpMethod.GET,"/CSS/**","/Images/**","/JavaScript/**").permitAll()
                                .requestMatchers(HttpMethod.POST,"/CSS/**", "/Images/**","/javascript/**").permitAll());
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
