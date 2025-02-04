package com.example.demo.Config;

import com.example.demo.security.JwtAuthenticationFilter;
import com.example.demo.security.WsAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration

public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter filter;
    @Autowired
    private WsAuthenticationFilter wsFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {})

                // Define authorization rules for specific HTTP requests first
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/index/**").authenticated() // Secure HTTP paths
                                .requestMatchers("/ws/**").authenticated()   // Secure WebSocket paths
                                .anyRequest().permitAll()  // Allow all other HTTP requests
                )

                // Session management (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Add JWT filter before UsernamePasswordAuthenticationFilter for HTTP requests
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)

                // Add WebSocket-specific filter
                .addFilterBefore(wsFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
