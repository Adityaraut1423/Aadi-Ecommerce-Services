package com.aadiandjava.config;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer; // 🌟 CORRECT IMPORT
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
            .cors(Customizer.withDefaults()) // Activates the CorsConfigurationSource bean below
            .csrf(csrf -> csrf.disable())    // Disabled for REST API usage
            .authorizeHttpRequests(auth -> auth
                    // Public Authentication Endpoints
                    .requestMatchers("/api/users/login", "/api/users/register").permitAll()
                    // Public Product Catalog Endpoints
                    .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                    // Allow preflight options requests
                    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // All other requests permitted for seamless full-stack communication
                    .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ==========================================
    // 3. GLOBAL CORS CONFIGURATION
    // ==========================================
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow requests from your deployed Firebase frontend and local environments
        configuration.setAllowedOrigins(Arrays.asList(
            "https://aadi-ecommerce-store-a3a0f.web.app",
            "http://localhost:3000",
            "http://127.0.0.1:5500",
            "http://localhost:8080"
        ));

        // Allow all standard HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Allow all headers
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}