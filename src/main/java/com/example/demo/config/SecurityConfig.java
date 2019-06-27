package com.example.demo.config;


import com.example.demo.service.CustomAccessDeniedHandler;
import com.example.demo.service.JwtAuthenticationTokenFilter;
import com.example.demo.service.RestAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() throws Exception {
		JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter = new JwtAuthenticationTokenFilter();
		jwtAuthenticationTokenFilter.setAuthenticationManager(authenticationManager());
		return jwtAuthenticationTokenFilter;
	}

	@Bean
	public RestAuthenticationEntryPoint restServicesEntryPoint() {
		return new RestAuthenticationEntryPoint();
	}

	@Bean
	public CustomAccessDeniedHandler customAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	protected void configure(HttpSecurity http) throws Exception {
		// Disable crsf cho đường dẫn /rest/**
		http.csrf().ignoringAntMatchers("/rest/**");
		http.authorizeRequests().antMatchers("/rest/login**").permitAll();
		http.csrf().ignoringAntMatchers("/web/**");
		http.authorizeRequests().antMatchers("/web/**").permitAll();
		http.antMatcher("/rest/**").httpBasic().authenticationEntryPoint(restServicesEntryPoint()).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().and()

//				.antMatchers(HttpMethod.GET, "/rest/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers(HttpMethod.POST, "/rest/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers(HttpMethod.POST, "/rest/upload/user/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
//				.antMatchers(HttpMethod.POST, "/rest/**").access("hasRole('ROLE_ADMIN')")
//				.antMatchers(HttpMethod.DELETE, "/rest/**").access("hasRole('ROLE_ADMIN')").and()
				.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
	}
}
