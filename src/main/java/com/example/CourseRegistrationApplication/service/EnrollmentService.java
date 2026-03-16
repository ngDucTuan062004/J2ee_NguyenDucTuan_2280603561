package com.example.CourseRegistrationApplication.service;

import com.example.CourseRegistrationApplication.model.*;
import com.example.CourseRegistrationApplication.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollmentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public Enrollment enroll(String username, Long courseId) {
		Student student = studentRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

		Course course = courseRepository.findById(courseId)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy học phần"));

		if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
			throw new RuntimeException("Bạn đã đăng ký học phần này rồi!");
		}

		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);
		enrollment.setEnrollDate(LocalDate.now());

		return enrollmentRepository.save(enrollment);
	}

	public List<Enrollment> getMyCourses(String username) {
		Student student = studentRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));
		return enrollmentRepository.findByStudent(student);
	}

	public boolean isEnrolled(String username, Long courseId) {
		try {
			Student student = studentRepository.findByUsername(username).orElse(null);
			Course course = courseRepository.findById(courseId).orElse(null);
			if (student == null || course == null)
				return false;
			return enrollmentRepository.existsByStudentAndCourse(student, course);
		} catch (Exception e) {
			return false;
		}
	}
}