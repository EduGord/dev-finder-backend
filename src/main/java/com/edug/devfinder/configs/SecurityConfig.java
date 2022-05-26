package com.edug.devfinder.configs;

import com.edug.devfinder.models.security.RolesEnum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        var roleHierarchy = new RoleHierarchyImpl();
        var hierarchy = String.join("\n",
                String.join(" > " , RolesEnum.ADMIN.name(), RolesEnum.STAFF.name()), // admin > staff
                        String.join(" > " , RolesEnum.STAFF.name(), RolesEnum.USER.name())); // staff > user
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        var expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    // Authentication
//    @Bean
//    public void configure(AuthenticationManagerBuilder auth) {}

    // Authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()
                .authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/h2-console/**").hasAnyAuthority("ADMIN") //.permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .build();
    }
}
