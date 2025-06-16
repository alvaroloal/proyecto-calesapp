package com.salesianostriana.dam.calesapp.security;

import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtAccessDeniedHandler;
import com.salesianostriana.dam.calesapp.security.exceptionhandling.JwtAuthenticationEntryPoint;
import com.salesianostriana.dam.calesapp.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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

                AuthenticationManagerBuilder authenticationManagerBuilder = http
                                .getSharedObject(AuthenticationManagerBuilder.class);

                AuthenticationManager authenticationManager = authenticationManagerBuilder
                                .authenticationProvider(authenticationProvider())
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
        @Order(1)
        public SecurityFilterChain swaggerSecurity(HttpSecurity http) throws Exception {
                http
                                .securityMatcher("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/webjars/**")
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(authz -> authz
                                                .anyRequest().hasAnyAuthority("ROLE_ADMIN"))
                                .httpBasic(Customizer.withDefaults());
                return http.build();
        }

        @Bean
        @Order(2)
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.csrf(AbstractHttpConfigurer::disable);
                http.cors(Customizer.withDefaults());
                http.sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.exceptionHandling(excepz -> excepz
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler));
                http.authorizeHttpRequests(authz -> authz
                                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login",
                                                "/auth/refresh/token")
                                .permitAll()
                                .requestMatchers(HttpMethod.PUT, "/auth/user/verify").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/paradas").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/paradas/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/paradas/buscar")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/paradas").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/paradas/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/paradas/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/servicios/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/servicios").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/servicios/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/servicios/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/contactos/buscar")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/contactos")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/contactos/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/contactos/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/contactos")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/valoraciones").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/valoraciones")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/valoraciones/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/valoraciones/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/cocheros/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/cocheros").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/cocheros/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/cocheros/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/usuarios/buscar")
                                .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/usuarios/**").hasAuthority("ROLE_ADMIN")
                // .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN")
                // .requestMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN")
                // .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN")
                );
                http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
                http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
                return http.build();
        }

}
