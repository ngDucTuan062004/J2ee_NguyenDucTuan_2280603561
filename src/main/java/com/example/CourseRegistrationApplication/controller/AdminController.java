package com.example.CourseRegistrationApplication.controller;

import com.example.CourseRegistrationApplication.model.*;
import com.example.CourseRegistrationApplication.repository.CategoryRepository;
import com.example.CourseRegistrationApplication.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private CourseService courseService;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private FileStorageService fileStorageService;

	@GetMapping
	public String adminDashboard(Model model) {
		model.addAttribute("courses", courseService.getAllCourses(0).getContent());
		return "admin/dashboard";
	}

	@GetMapping("/courses/new")
	public String newCourseForm(Model model) {
		model.addAttribute("course", new Course());
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/course-form";
	}

	@PostMapping("/courses/save")
	public String saveCourse(@Valid @ModelAttribute("course") Course course,
			BindingResult result,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
			Model model) {
		if (result.hasErrors()) {
			model.addAttribute("categories", categoryRepository.findAll());
			return "admin/course-form";
		}
		try {
			if (imageFile != null && !imageFile.isEmpty()) {
				String imageUrl = fileStorageService.storeFile(imageFile);
				course.setImage(imageUrl);
			} else if (course.getImage() == null || course.getImage().isEmpty()) {
				course.setImage("/images/default-course.png");
			}
			courseService.save(course);
			return "redirect:/admin?success=true";
		} catch (Exception e) {
			model.addAttribute("error", "Lỗi lưu học phần: " + e.getMessage());
			model.addAttribute("categories", categoryRepository.findAll());
			return "admin/course-form";
		}
	}

	@GetMapping("/courses/edit/{id}")
	public String editCourseForm(@PathVariable Long id, Model model) {
		Course course = courseService.findById(id)
				.orElseThrow(() -> new RuntimeException("Không tìm thấy học phần"));
		model.addAttribute("course", course);
		model.addAttribute("categories", categoryRepository.findAll());
		return "admin/course-form";
	}

	@GetMapping("/courses/delete/{id}")
	public String deleteCourse(@PathVariable Long id) {
		courseService.deleteById(id);
		return "redirect:/admin?deleted=true";
	}
}