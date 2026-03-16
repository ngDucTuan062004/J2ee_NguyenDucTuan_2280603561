package com.example.CourseRegistrationApplication.service;

import com.example.CourseRegistrationApplication.model.Role;
import com.example.CourseRegistrationApplication.model.Student;
import com.example.CourseRegistrationApplication.repository.RoleRepository;
import com.example.CourseRegistrationApplication.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Student register(Student student) {
		if (studentRepository.existsByUsername(student.getUsername())) {
			throw new RuntimeException("Username đã tồn tại!");
		}
		if (studentRepository.existsByEmail(student.getEmail())) {
			throw new RuntimeException("Email đã tồn tại!");
		}

		student.setPassword(passwordEncoder.encode(student.getPassword()));

		Role studentRole = roleRepository.findByName("STUDENT")
				.orElseThrow(() -> new RuntimeException("Role STUDENT không tồn tại"));

		Set<Role> roles = new HashSet<>();
		roles.add(studentRole);
		student.setRoles(roles);

		return studentRepository.save(student);
	}

	public Optional<Student> findByUsername(String username) {
		return studentRepository.findByUsername(username);
	}

	public Optional<Student> findByEmail(String email) {
		return studentRepository.findByEmail(email);
	}

	// OAuth2: Tạo hoặc lấy student từ Google
	public Student findOrCreateOAuthStudent(String email, String name) {
		Optional<Student> existing = studentRepository.findByEmail(email);
		if (existing.isPresent()) {
			return existing.get();
		}

		Student student = new Student();
		student.setEmail(email);
		student.setUsername(email);
		student.setPassword(passwordEncoder.encode("oauth2_" + System.currentTimeMillis()));

		Role studentRole = roleRepository.findByName("STUDENT")
				.orElseThrow(() -> new RuntimeException("Role STUDENT không tồn tại"));

		Set<Role> roles = new HashSet<>();
		roles.add(studentRole);
		student.setRoles(roles);

		return studentRepository.save(student);
	}
}