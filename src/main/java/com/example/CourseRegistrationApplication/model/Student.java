package com.example.CourseRegistrationApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.*;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "student_id")
	private Long studentId;

	@NotBlank(message = "Username không được để trống")
	@Column(name = "username", unique = true, nullable = false, length = 100)
	private String username;

	@NotBlank(message = "Password không được để trống")
	@Column(name = "password", nullable = false)
	private String password;

	@Email(message = "Email không hợp lệ")
	@NotBlank(message = "Email không được để trống")
	@Column(name = "email", unique = true, nullable = false, length = 150)
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "student_role", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments = new ArrayList<>();
}