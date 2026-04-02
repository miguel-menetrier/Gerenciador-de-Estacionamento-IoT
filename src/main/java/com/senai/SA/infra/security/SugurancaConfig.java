package com.senai.SA.infra.security;

import com.senai.SA.infra.CustomAuthenticationFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // ✅ enables @PreAuthorize
@RequiredArgsConstructor
public class SugurancaConfig {

    private final CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain filtrosSeguranca(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> {
                    req.requestMatchers("/script/**", "/img/**", "/css/**", "/cadastro", "/login").permitAll();

                    // ✅ ROLE RULES
                    req.requestMatchers("/admin/**").hasRole("ADMIN");
                    req.requestMatchers("/user/**").hasRole("USER");

                    req.anyRequest().authenticated();
                })
                .formLogin(form -> form.loginPage("/login")
                        .failureHandler(failureHandler)
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll())
                .build();
    }

    // ✅ ROLE HIERARCHY (ADMIN > USER)
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public PasswordEncoder codificarSenha() {
        return new BCryptPasswordEncoder();
    }
}