package com.example.CourseRegistrationApplication.service;

import com.example.CourseRegistrationApplication.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

	@Autowired
	private StudentService studentService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);

		String email = oAuth2User.getAttribute("email");
		String name = oAuth2User.getAttribute("name");

		Student student = studentService.findOrCreateOAuthStudent(email, name);

		Set<GrantedAuthority> authorities = student.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
				.collect(Collectors.toSet());

		Map<String, Object> attributes = new HashMap<>(oAuth2User.getAttributes());

		return new DefaultOAuth2User(authorities, attributes, "email");
	}
}