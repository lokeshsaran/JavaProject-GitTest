package com.springbootassignment.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootassignment.form.UserDetailForm;
import com.springbootassignment.model.User;
import com.springbootassignment.repository.UserDetailRepository;

@Controller
public class LoginController {
	
	@Autowired
	UserDetailRepository userDetailRepository;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			@RequestParam(value = "signup", required = false) String signup, Model model) {
		String errorMessge = null;
		if (error != null) {
			errorMessge = "Username or Password is incorrect !!";
		}
		if (logout != null) {
			errorMessge = "You have been successfully logged out !!";
		}
		if (signup != null) {
			errorMessge = "User added successfully !!";
		}
		model.addAttribute("errorMessge", errorMessge);
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout=true";
	}

	@GetMapping("/home")
	public String homeInit(Locale locale, Model model) {
		return "home";
	}
	
	@GetMapping("/signUp")
	public String AddUser(UserDetailForm userDetailForm, Model model) {
		model.addAttribute("userDetailForm", userDetailForm);
		return "signUpUser";
	}
	
	@PostMapping("/signUpSubmit")
	public String SaveUser(@Valid UserDetailForm userDetailForm, Model model, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("userDetailForm", userDetailForm);
			return "signUpUser";
		}
		
		User user = new User();
		user.setUserName(userDetailForm.getUserName());
		user.setCity(userDetailForm.getCity());
		user.setPassword(userDetailForm.getPassword());
		userDetailRepository.save(user);
		return "redirect:/login?signup=true";
	}
}
