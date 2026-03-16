package com.example.CourseRegistrationApplication.controller;

import com.example.CourseRegistrationApplication.model.Student;
import com.example.CourseRegistrationApplication.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/login")
	public String loginPage() {
		return "auth/login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("student", new Student());
		return "auth/register";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("student") Student student,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "auth/register";
		}
		try {
			studentService.register(student);
			return "redirect:/login?registered=true";
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			return "auth/register";
		}
	}
}