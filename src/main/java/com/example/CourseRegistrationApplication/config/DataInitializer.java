package com.example.CourseRegistrationApplication.config;

import com.example.CourseRegistrationApplication.model.*;
import com.example.CourseRegistrationApplication.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {

		/*
		 * =========================
		 * TẠO ROLE
		 * =========================
		 */

		if (roleRepository.count() == 0) {

			roleRepository.save(new Role(null, "ADMIN", null));
			roleRepository.save(new Role(null, "STUDENT", null));

		}

		/*
		 * =========================
		 * TẠO ADMIN
		 * =========================
		 */

		if (!studentRepository.existsByUsername("admin")) {

			Student admin = new Student();

			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setEmail("admin@eduportal.com");

			Role adminRole = roleRepository.findByName("ADMIN").orElseThrow();

			admin.setRoles(Set.of(adminRole));

			studentRepository.save(admin);
		}

		/*
		 * =========================
		 * TẠO STUDENT DEMO
		 * =========================
		 */

		if (!studentRepository.existsByUsername("student")) {

			Student student = new Student();

			student.setUsername("student");
			student.setPassword(passwordEncoder.encode("123456"));
			student.setEmail("student@gmail.com");

			Role role = roleRepository.findByName("STUDENT").orElseThrow();

			student.setRoles(Set.of(role));

			studentRepository.save(student);
		}

		/*
		 * =========================
		 * TẠO CATEGORY
		 * =========================
		 */

		if (categoryRepository.count() == 0) {

			categoryRepository.save(new Category(null, "Công nghệ thông tin", null));

			categoryRepository.save(new Category(null, "Trí tuệ nhân tạo", null));

			categoryRepository.save(new Category(null, "Kinh tế - Marketing", null));

			categoryRepository.save(new Category(null, "Ngoại ngữ", null));

		}

		/*
		 * =========================
		 * TẠO COURSE
		 * =========================
		 */

		if (courseRepository.count() == 0) {

			List<Category> categories = categoryRepository.findAll();

			Category it = categories.get(0);
			Category ai = categories.get(1);
			Category business = categories.get(2);
			Category language = categories.get(3);

			List<Course> courses = List.of(

					createCourse("Lập trình Java cơ bản",
							3,
							"TS. Nguyễn Văn An",
							"java.jpg",
							it),

					createCourse("Spring Boot Web Development",
							4,
							"ThS. Trần Minh Khôi",
							"springboot.jpg",
							it),

					createCourse("Cơ sở dữ liệu",
							3,
							"PGS. Lê Hoàng Long",
							"database.jpg",
							it),

					createCourse("Thuật toán và cấu trúc dữ liệu",
							4,
							"TS. Phạm Tuấn Kiệt",
							"algorithm.jpg",
							it),

					createCourse("Machine Learning cơ bản",
							4,
							"TS. Nguyễn Đức Huy",
							"machinelearning.jpg",
							ai),

					createCourse("Deep Learning",
							4,
							"PGS. Trần Quốc Bảo",
							"deeplearning.jpg",
							ai),

					createCourse("Trí tuệ nhân tạo ứng dụng",
							3,
							"TS. Hoàng Anh Tuấn",
							"ai.jpg",
							ai),

					createCourse("Marketing Digital",
							3,
							"ThS. Lê Thị Lan",
							"marketing.jpg",
							business),

					createCourse("Quản trị doanh nghiệp",
							3,
							"PGS. Nguyễn Hữu Phước",
							"business.jpg",
							business),

					createCourse("Tiếng Anh giao tiếp",
							2,
							"Mr. John Smith",
							"english.jpg",
							language),

					createCourse("IELTS Preparation",
							3,
							"Ms. Sarah Brown",
							"ielts.jpg",
							language),

					createCourse("Tiếng Nhật N5",
							2,
							"Sensei Tanaka",
							"japanese.jpg",
							language)

			);

			courseRepository.saveAll(courses);

		}

	}

	/*
	 * =========================
	 * HELPER METHOD
	 * =========================
	 */

	private Course createCourse(
			String name,
			int credits,
			String lecturer,
			String image,
			Category category) {

		Course c = new Course();

		c.setName(name);

		c.setCredits(credits);

		c.setLecturer(lecturer);

		c.setImage(image);

		c.setCategory(category);

		return c;
	}

}
