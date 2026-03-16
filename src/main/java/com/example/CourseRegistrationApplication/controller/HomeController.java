package com.example.CourseRegistrationApplication.controller;

import com.example.CourseRegistrationApplication.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

	@Autowired
	private CourseService courseService;

	@GetMapping({ "/", "/home" })
	public String home(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String keyword,
			Model model) {
		var coursePage = courseService.searchCourses(keyword, page);
		model.addAttribute("coursePage", coursePage);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalPages", coursePage.getTotalPages());
		return "home";
	}

	@GetMapping("/courses")
	public String courses(
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(required = false) String keyword,
			Model model) {
		var coursePage = courseService.searchCourses(keyword, page);
		model.addAttribute("coursePage", coursePage);
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("totalPages", coursePage.getTotalPages());
		return "home";
	}
}