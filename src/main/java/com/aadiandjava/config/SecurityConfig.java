package com.aadiandjava.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

    // ==========================================
    // 1. PASSWORD ENCRYPTION BEAN
    // ==========================================
    // Scrambles passwords into secure hashes before saving to MySQL
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ==========================================
    // 2. SECURITY FILTER CHAIN (ENDPOINT RULES)
    // ==========================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults()) // Uses the CORS bean below
            .csrf(csrf -> csrf.disable())    // Disabled for REST APIs
            .authorizeHttpRequests(auth -> auth
                    // Public Endpoints (Anyone can access)
                    .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                    
                    // NOTE: We are leaving the rest as permitAll() temporarily.
                    // Once we implement JWT (JSON Web Tokens) in the frontend, 
                    // we will change this to .authenticated() and .hasRole("ADMIN")!
                    .anyRequest().permitAll() 
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ==========================================
    // 3. CORS CONFIGURATION
    // ==========================================
    // Prevents the browser from blocking requests between your HTML and Spring Boot
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow all frontend origins
        configuration.setAllowedOrigins(Arrays.asList("*"));

        // Allow all standard HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Apply these rules to every single API endpoint
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}