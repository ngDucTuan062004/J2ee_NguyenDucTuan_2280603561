package com.example.CourseRegistrationApplication.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id", nullable = false)
	private Student student;

	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	@Column(name = "enroll_date", nullable = false)
	private LocalDate enrollDate;

	@PrePersist
	public void prePersist() {
		if (enrollDate == null) {
			enrollDate = LocalDate.now();
		}
	}
}