package com.product.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.product.app.config.CorsConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final CorsConfig corsConfig;
	private final CustomAuthenticationEntryPoint authenticationEntryPoint;
	private final CustomAccessDeniedHandler accessDeniedHandler;

	private static final String PRODUCTS_BASE = "/api/v1/products";

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http.csrf(csrf -> csrf.disable()).cors(cors -> cors.configurationSource(corsConfig.corsConfigurer()))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint)
						.accessDeniedHandler(accessDeniedHandler))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				.authorizeHttpRequests(auth -> auth

						// -----------------------------
						// PUBLIC (Swagger)
						// -----------------------------
						.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

						// -----------------------------
						// USER + ADMIN (READ only)
						// -----------------------------
						.requestMatchers(HttpMethod.GET, PRODUCTS_BASE + "/pagination", PRODUCTS_BASE + "/*")
						.hasAnyRole("USER", "ADMIN")

						// -----------------------------
						// ADMIN (WRITE operations)
						// -----------------------------
						.requestMatchers(HttpMethod.POST, PRODUCTS_BASE).hasRole("ADMIN")

						.requestMatchers(HttpMethod.PUT, PRODUCTS_BASE + "/*").hasRole("ADMIN")

						.requestMatchers(HttpMethod.DELETE, PRODUCTS_BASE + "/*").hasRole("ADMIN")

						// -----------------------------
						// EVERYTHING ELSE
						// -----------------------------
						.anyRequest().authenticated())

				.httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	UserDetailsService userDetailsService(PasswordEncoder encoder) {

		UserDetails user = User.withUsername("user").password(encoder.encode("user123")).roles("USER").build();

		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin123")).roles("ADMIN").build();

		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
