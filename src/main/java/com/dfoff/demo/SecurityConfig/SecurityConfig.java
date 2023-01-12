package com.dfoff.demo.SecurityConfig;

import com.dfoff.demo.Util.Bcrypt;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.beans.BeanProperty;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig {
    private final SecurityService securityService;
    private final Bcrypt bcrypt;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.formLogin(form -> form
                .loginPage("/api/user/login")
                        .usernameParameter("username").passwordParameter("password")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/api/user/login?error")
        )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/user/logout"))
                        .logoutSuccessUrl("/")
                ).csrf().disable()
                .authorizeHttpRequests(authorize -> authorize.requestMatchers("/**").permitAll()
                        .anyRequest().authenticated()
                )
                .build();


    }



    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(bcrypt.getInstance());
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
                        .allowCredentials(true);
            }
        };
    }


    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }




}
