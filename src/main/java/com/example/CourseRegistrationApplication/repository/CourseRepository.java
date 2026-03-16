package com.example.CourseRegistrationApplication.repository;

import com.example.CourseRegistrationApplication.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	Page<Course> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Course> findAll(Pageable pageable);
}