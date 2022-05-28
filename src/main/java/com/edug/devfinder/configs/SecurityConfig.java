package com.edug.devfinder.configs;

import com.edug.devfinder.models.security.PermissionEnum;
import com.edug.devfinder.models.security.RolesEnum;
import com.edug.devfinder.repositories.PermissionRepository;
import com.edug.devfinder.repositories.UserRepository;
import com.edug.devfinder.security.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig implements AuthenticationConstants {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Resource
    private final ObjectMapper objectMapper;

    @Bean
    public RoleHierarchy roleHierarchy() {
        var roleHierarchy = new RoleHierarchyImpl();
        var hierarchy = String.join("\n",
                String.join(" > ", RolesEnum.ADMIN.name(), RolesEnum.STAFF.name()), // admin > staff
                String.join(" > ", RolesEnum.STAFF.name(), RolesEnum.USER.name())); // staff > user
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        var expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(AuthenticationConstants.DUMMY_SECRET, 10000, 512);
    }

    @Bean
    public CorsFilter corsFilter() {
        var source = new UrlBasedCorsConfigurationSource();
        var config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public AuthenticationManager customAuthenticationProvider() {
        return new ProviderManager(List.of(new CustomAuthenticationProvider(userRepository,  permissionRepository, passwordEncoder())));
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler(objectMapper);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        var customAuthenticationFilter = new CustomAuthenticationFilter(customAuthenticationProvider(), objectMapper);
        customAuthenticationFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler());
        httpSecurity.cors()
                .and()
                .csrf().disable()
                .headers().frameOptions().sameOrigin();

        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity
                .authorizeRequests()
                .antMatchers("/login", "/h2-console", "/user/refresh-token**").permitAll()
                .antMatchers("/user/**").hasAuthority(PermissionEnum.READ_USER.name())
                .antMatchers("/technology/**").hasAuthority(PermissionEnum.READ_TECHNOLOGY.name())
                .antMatchers(ADMIN_ONLY_PATTERNS).hasRole(RolesEnum.ADMIN.name())
                .anyRequest().authenticated();

        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(objectMapper), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
