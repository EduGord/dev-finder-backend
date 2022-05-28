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
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SecurityConfig {

    private final UserRepository userRepository;
    private final PermissionRepository permissionRepository;

    @Resource
    private final ObjectMapper objectMapper;

    private final String[] ADMIN_ONLY_PATTERNS = {
            "/actuator",
            "/actuator/{(?<=health).+(?=)}",
            "/swagger-ui/**"
    };

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

//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }
//
//
//    @Bean
//    public UserDetailsManager userDetailsManager() {
//        return new JdbcUserDetailsManager(dataSource());
//    }

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

    // TODO
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
                .antMatchers(ADMIN_ONLY_PATTERNS).hasRole(RolesEnum.ADMIN.name());
//                .anyRequest().authenticated();
        httpSecurity.formLogin().failureHandler(customAuthenticationFailureHandler());

        httpSecurity.addFilter(customAuthenticationFilter);
        httpSecurity.addFilterBefore(new CustomAuthorizationFilter(objectMapper), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    private RequestMatcher getRequestMatchers(String... pattern) {
        return new OrRequestMatcher(Arrays.stream(pattern)
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList()));
    }
}
