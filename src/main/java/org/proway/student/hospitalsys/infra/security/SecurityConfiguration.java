package org.proway.student.hospitalsys.infra.security;

import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/v1/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/api/auth/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/v1/api/auth/register/receptionist").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.POST, "/v1/api/auth/register/doctor").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.GET, "/v1/api/appointment/doctor/").hasAnyRole("ADMIN", "RECEPTIONIST", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/v1/api/appointment").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                        .requestMatchers(HttpMethod.POST, "/v1/api/appointment").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .requestMatchers(HttpMethod.GET, "/v1/api/appointment/patient/").permitAll()
                        .requestMatchers(HttpMethod.GET, "/v1/api/user/doctor").hasAnyRole("ADMIN", "RECEPTIONIST", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/v1/api/user/patient").hasAnyRole("ADMIN", "RECEPTIONIST", "DOCTOR")
                        .requestMatchers(HttpMethod.GET, "/v1/api/user").hasAnyRole("ADMIN", "RECEPTIONIST")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
