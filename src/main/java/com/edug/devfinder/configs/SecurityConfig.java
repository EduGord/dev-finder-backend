package com.edug.devfinder.configs;

import com.edug.devfinder.models.enums.PermissionEnum;
import com.edug.devfinder.models.enums.RolesEnum;
import com.edug.devfinder.security.*;
import com.edug.devfinder.services.LoginAttemptsService;
import com.edug.devfinder.services.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
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
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig implements AuthenticationConstants {
    @Resource
    private final ObjectMapper objectMapper;


    @Resource

    private final LoginAttemptsService loginAttemptsService;

    private final EntityManagerFactory entityManagerFactory;

    private final HttpServletRequest request;

    @EnableGlobalMethodSecurity(
            prePostEnabled = true,
            securedEnabled = true,
            jsr250Enabled = true)
    public static class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {}

    @Bean
    protected UserDetailsServiceImpl userDetailsService() {
        return new UserDetailsServiceImpl(entityManagerFactory, loginAttemptsService, request);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        var roleHierarchy = new RoleHierarchyImpl();
        var hierarchy = String.join("\n",
                String.join(" > ", RolesEnum.ADMIN.name(), RolesEnum.STAFF.name()), // admin > staff
                String.join(" > ", RolesEnum.STAFF.name(), RolesEnum.RECRUITER.name()), // staff > recruiter
                String.join(" > ", RolesEnum.RECRUITER.name(), RolesEnum.USER.name())); // recruiter > user
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
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(passwordEncoder(), userDetailsService());
    }

    @Bean
    public CustomAuthenticationFailureHandler customAuthenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler(objectMapper, loginAttemptsService);
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
                .antMatchers(ADMIN_ONLY_PATTERNS).hasAuthority(RolesEnum.ADMIN.name())
                .antMatchers("/login/**", "/user/register", "/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET, "/security/refresh-token/**").permitAll()
                .antMatchers("/user/**").hasAuthority(PermissionEnum.READ_USER.name())
                .antMatchers("/technology/**").hasAuthority(PermissionEnum.READ_TECHNOLOGY.name())
                .anyRequest().authenticated()
                .expressionHandler(webSecurityExpressionHandler());
//                .and().formLogin();

        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(objectMapper), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
