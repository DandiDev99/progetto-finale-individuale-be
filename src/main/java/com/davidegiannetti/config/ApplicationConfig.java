package com.davidegiannetti.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.davidegiannetti.entity.User;
import com.davidegiannetti.repository.UserRepository;
import com.davidegiannetti.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.io.IOException;

@Configuration
public class ApplicationConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JWTUtil jwtUtil, ObjectMapper objectMapper, UserRepository userRepository) throws Exception {
        httpSecurity.csrf(csrfConfigurer -> csrfConfigurer.disable());
        httpSecurity.cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("http://localhost:4200/");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
            return corsConfiguration;
        }));
        httpSecurity.sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
            //TODO : configuare tutti gli endpoint con i vari permessi
            authorizationManagerRequestMatcherRegistry.requestMatchers("/error", "/**").permitAll();
        });

        httpSecurity.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
                String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                if(header != null && header.startsWith(jwtUtil.getPrefix())){
                    try{
                        String jwt = header.replace(jwtUtil.getPrefix(), "");
                        DecodedJWT decodedJWT = jwtUtil.decode(jwt);
                        Claim userClaim = decodedJWT.getClaim("user");
                        User user = objectMapper.readValue(userClaim.asString(), User.class);
                        userRepository.findById(user.getId()).ifPresent(u ->{
                            System.out.println(u);
                            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u.getEmail(), null, u.getRoles()));
                        });
                    }catch (TokenExpiredException e){
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        response.setStatus(401);
                        response.getWriter().write("Token scaduto");
                        return;
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                filterChain.doFilter(servletRequest, servletResponse);
            }
        },UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
