package com.example.CourseRegistrationApplication.controller;

import com.example.CourseRegistrationApplication.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/enroll")
public class EnrollController {

	@Autowired
	private EnrollmentService enrollmentService;

	@PostMapping("/{courseId}")
	public String enroll(@PathVariable Long courseId,
			Authentication authentication,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false) String keyword) {
		try {
			String username = authentication.getName();
			enrollmentService.enroll(username, courseId);
			return "redirect:/home?page=" + page + "&enrolled=true";
		} catch (RuntimeException e) {
			return "redirect:/home?page=" + page + "&error=" + e.getMessage();
		}
	}

	@GetMapping("/my-courses")
	public String myCourses(Authentication authentication, Model model) {
		String username = authentication.getName();
		model.addAttribute("enrollments", enrollmentService.getMyCourses(username));
		return "student/my-courses";
	}
}