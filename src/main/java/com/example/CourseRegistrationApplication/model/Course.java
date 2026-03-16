package com.example.CourseRegistrationApplication.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Tên học phần không được để trống")
	@Column(name = "name", nullable = false, length = 200)
	private String name;

	@Column(name = "image", length = 500)
	private String image;

	@Min(value = 1, message = "Số tín chỉ phải lớn hơn 0")
	@Max(value = 10, message = "Số tín chỉ không được vượt quá 10")
	@Column(name = "credits", nullable = false)
	private Integer credits;

	@NotBlank(message = "Tên giảng viên không được để trống")
	@Column(name = "lecturer", nullable = false, length = 150)
	private String lecturer;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	private List<Enrollment> enrollments;
}