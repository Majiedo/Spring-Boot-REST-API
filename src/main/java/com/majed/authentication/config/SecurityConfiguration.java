package com.majed.authentication.config;

import com.majed.authentication.domain.entities.Role;
import com.majed.authentication.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(requestMatcher ->
                requestMatcher
                        .requestMatchers("auth/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users").hasAuthority(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.PATCH, "/api/users/").hasAuthority(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE, "/api/users/").hasAuthority(Role.ADMIN.toString())
                        .anyRequest().authenticated()
        );

        httpSecurity.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authenticationProvider(authenticationProvider);

        httpSecurity.exceptionHandling((exception) -> exception.authenticationEntryPoint(customAuthenticationEntryPoint));

        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
