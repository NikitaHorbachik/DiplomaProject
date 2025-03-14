package org.nharbachyk.diplomabackend.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {
<<<<<<< Updated upstream
=======

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter jwtAuthFilter,
                                           CustomBasicAuthenticationFilter customBasicAuthFilter,
                                           RefreshJwtFilter refreshJwtFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/api/v1/auth").permitAll()
                                .requestMatchers("/api/v1/refresh").permitAll()
                                .requestMatchers("/error").permitAll()
                                .requestMatchers("/api/v1/**").authenticated()
                                .requestMatchers("/api/v1/logout").authenticated()
                                .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                                .accessDeniedHandler(new AccessDeniedHandlerImpl()))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(refreshJwtFilter, JwtAuthenticationFilter.class)
                .addFilterAfter(customBasicAuthFilter, jwtAuthFilter.getClass());
        return http.build();
    }

>>>>>>> Stashed changes
}
