package com.example.CourseRegistrationApplication.config;

import com.example.CourseRegistrationApplication.service.CustomOAuth2UserService;
import com.example.CourseRegistrationApplication.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private CustomOAuth2UserService oAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/enroll/**").hasRole("STUDENT")
						.requestMatchers("/my-courses").hasRole("STUDENT")
						.requestMatchers("/courses", "/home", "/", "/register", "/login",
								"/css/**", "/js/**", "/images/**", "/uploads/**")
						.permitAll()
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.usernameParameter("username")
						.passwordParameter("password")
						.defaultSuccessUrl("/home", true)
						.failureUrl("/login?error=true")
						.permitAll())
				.oauth2Login(oauth2 -> oauth2
						.loginPage("/login")
						.userInfoEndpoint(u -> u.userService(oAuth2UserService))
						.defaultSuccessUrl("/home", true))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout=true")
						.permitAll())
				.userDetailsService(userDetailsService);

		return http.build();
	}
}