package com.example.CourseRegistrationApplication.service;

import com.example.CourseRegistrationApplication.model.Course;
import com.example.CourseRegistrationApplication.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	// Lấy tất cả courses có phân trang (5 per page)
	public Page<Course> getAllCourses(int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		return courseRepository.findAll(pageable);
	}

	// Tìm kiếm theo tên có phân trang
	public Page<Course> searchCourses(String keyword, int page) {
		Pageable pageable = PageRequest.of(page, 5, Sort.by("id").descending());
		if (keyword == null || keyword.trim().isEmpty()) {
			return courseRepository.findAll(pageable);
		}
		return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
	}

	public Optional<Course> findById(Long id) {
		return courseRepository.findById(id);
	}

	public Course save(Course course) {
		return courseRepository.save(course);
	}

	public void deleteById(Long id) {
		courseRepository.deleteById(id);
	}
}