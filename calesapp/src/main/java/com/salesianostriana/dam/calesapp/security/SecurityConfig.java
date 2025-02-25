package com.salesianostriana.dam.calesapp.security;

import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtAccessDeniedHandler;
import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtAuthenticationEntryPoint;
import com.salesianostriana.dam.calesapp.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        AuthenticationManager authenticationManager =
                authenticationManagerBuilder.authenticationProvider(authenticationProvider())
                        .build();

        return authenticationManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder);
        return p;

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(Customizer.withDefaults());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(excepz -> excepz
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );
        /*http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token").permitAll()
                .requestMatchers(HttpMethod.GET,"/me/admin").hasRole("ADMIN")
                .requestMatchers("/h2-console/**", "/auth/user/verify/**").permitAll()
                .anyRequest().authenticated());*/
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET,"/api/paradas").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/paradas/{id}").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/servicios/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/paradas/buscar").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/contactos").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/valoraciones").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token").permitAll()
                .requestMatchers(HttpMethod.PUT, "/auth/user/verify").permitAll()



        );


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }




}

