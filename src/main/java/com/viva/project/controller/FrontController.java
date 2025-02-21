package com.viva.project.controller;

import java.util.Optional;

import com.viva.project.entity.UserAuthEntity;
import com.viva.project.model.UserModel;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.viva.project.service.UserAuthService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FrontController {

	@Autowired
	private UserAuthService userAuthService;

	//Load the landing page
	@GetMapping("/")
	public String viewLandPage(@RequestParam(value = "logout", required = false) boolean logout, Model model, HttpSession session) {
		model.addAttribute("logout", logout);
		session.setAttribute("loggedIn", false);
		return "landpage";
	}


	@GetMapping("/login")
	public String entryLogin(@RequestParam(value = "error", required = false) boolean error, Model model) {
		UserModel userModel = new UserModel();
		model.addAttribute("user", userModel);
		model.addAttribute("error", error);
		return "login";
	}

	@PostMapping("/login")
	public String submitLogin(@ModelAttribute("user") UserModel userModel, Model model, HttpSession session, RedirectAttributes redirectAttributes) {
		Optional<String> username = Optional.of(userModel)
				.map(UserModel::getUsername)
				.map(Object::toString)
				.filter(str -> !str.isBlank());

		Optional<String> password = Optional.of(userModel)
				.map(UserModel::getPassword)
				.map(Object::toString)
				.filter(str -> !str.isBlank());

		if (username.isPresent() && password.isPresent()) {
			Optional<UserAuthEntity> user = userAuthService.getUser(username.get(), password.get());

			if (user.isPresent()) {
				String dbPassword = user.get().getPassword();
				String role = user.get().getRole();

				boolean loginSuccessful = dbPassword.equals(userModel.getPassword());

				if (loginSuccessful) {
					session.setAttribute("loggedIn", true);
					if ("ADMIN".equals(role)) {
						return "redirect:/admin_profile";
					} else {
						return "redirect:/profile";
					}
				} else {
					session.setAttribute("loggedIn", false);
					redirectAttributes.addFlashAttribute("errorMsg", "Username or password incorrect");
				}
			} else {
				session.setAttribute("loggedIn", false);
				redirectAttributes.addFlashAttribute("errorMsg", "User not found!");
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMsg", "Username and password cannot be empty / blank");
			session.setAttribute("loggedIn", false);
		}
		return "redirect:/login?error=true";
	}

	@GetMapping("/register")
	public String entryRegister(@RequestParam(value = "error", required = false) boolean error, Model model) {
		UserModel userModel = new UserModel();
		model.addAttribute("user", userModel);
		model.addAttribute("error", error);
		return "register";
	}

	@PostMapping("/register")
	public String submitRegister(@ModelAttribute("user") UserModel userModel, Model model, RedirectAttributes redirectAttributes) {
		Optional<String> username = Optional.of(userModel)
				.map(UserModel::getUsername)
				.map(Object::toString)
				.filter(str -> !str.isBlank());

		Optional<String> password = Optional.of(userModel)
				.map(UserModel::getPassword)
				.map(Object::toString)
				.filter(str -> !str.isBlank());

		if (username.isPresent() && password.isPresent()) {
			try {
				UserAuthEntity userAuthEntity = userAuthService.doUserRegistration(username.get(), password.get(), userModel.getRole());
				userModel.setId(userAuthEntity.getId());
				redirectAttributes.addFlashAttribute("user", userModel);

				return "redirect:/login";
			} catch (Exception exception) {
				redirectAttributes.addFlashAttribute("errorMsg", exception.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMsg", "Username and password cannot be empty / blank");
		}

		return "redirect:/register?error=true";
	}

	@GetMapping("/logout")
	public String entryLogout(HttpSession session, Model model) {
		session.invalidate();
		return "redirect:/";
	}

	@GetMapping("/profile")
	public String viewProfilePage(Model model) {
		return "student_dashboard";
	}

	@GetMapping("/admin_profile")
	public String viewAdminProfilePage(Model model) {
		return "admin_dashboard";
	}
//
//	@GetMapping("/showNewEmployeeForm")
//	public String showNewEmployeeForm(Model model) {
//		// create model attribute to bind form data
//		Employee employee = new Employee();
//		model.addAttribute("employee", employee);
//		return "new_employee";
//	}
//
//	@PostMapping("/saveEmployee")
//	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
//		// save employee to database
//		employeeService.saveEmployee(employee);
//		return "redirect:/";
//	}
//
//	@GetMapping("/showFormForUpdate/{id}")
//	public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {
//
//		// get employee from the service
//		Employee employee = employeeService.getEmployeeById(id);
//
//		// set employee as a model attribute to pre-populate the form
//		model.addAttribute("employee", employee);
//		return "update_employee";
//	}
//
//	@GetMapping("/deleteEmployee/{id}")
//	public String deleteEmployee(@PathVariable (value = "id") long id) {
//
//		// call delete employee method
//		this.employeeService.deleteEmployeeById(id);
//		return "redirect:/";
//	}
//
//
//	@GetMapping("/page/{pageNo}")
//	public String findPaginated(@PathVariable (value = "pageNo") int pageNo,
//			@RequestParam("sortField") String sortField,
//			@RequestParam("sortDir") String sortDir,
//			Model model) {
//		int pageSize = 5;
//
//		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
//		List<Employee> listEmployees = page.getContent();
//		boolean isLoggedIn = "Y".equals(model.getAttribute("loggedIn"));
//		String sessionId = Optional.ofNullable(model.getAttribute("sessionId"))
//				.map(Object::toString)
//				.orElse("");
//
//
//        model.addAttribute("currentPage", pageNo);
//		model.addAttribute("totalPages", page.getTotalPages());
//		model.addAttribute("totalItems", page.getTotalElements());
//
//		model.addAttribute("sortField", sortField);
//		model.addAttribute("sortDir", sortDir);
//		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
//
//		model.addAttribute("listEmployees", listEmployees);
//		return "landpage";
//	}
}
