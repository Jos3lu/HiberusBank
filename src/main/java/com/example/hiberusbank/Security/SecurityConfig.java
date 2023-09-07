package com.example.hiberusbank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthProvider authProvider;
	
	//@Autowired
	//private AuthFilter authFilter;

    @Bean
    AuthenticationManager authManager(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = 
				http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.authenticationProvider(authProvider);
		return authenticationManagerBuilder.build();
	}
    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
    	MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		http.authorizeHttpRequests(authz -> authz
				.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/api/workers/*/payroll")).authenticated()
				.requestMatchers(mvcMatcherBuilder.pattern("/h2-console/**")).permitAll()
				.anyRequest().permitAll());
		
		// For H2 Database console
		http.csrf(csrf -> csrf.disable());
		http.headers(authz -> authz.frameOptions(authz1 -> authz1.disable()));
		
		http.authenticationProvider(authProvider);
		
		//http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
	
}
