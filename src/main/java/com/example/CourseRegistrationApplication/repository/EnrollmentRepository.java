package com.example.CourseRegistrationApplication.repository;

import com.example.CourseRegistrationApplication.model.Enrollment;
import com.example.CourseRegistrationApplication.model.Student;
import com.example.CourseRegistrationApplication.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	List<Enrollment> findByStudent(Student student);

	Optional<Enrollment> findByStudentAndCourse(Student student, Course course);

	boolean existsByStudentAndCourse(Student student, Course course);
}