package com.erp.Coffee.controller.admin;

import com.erp.Coffee.controller.admin.exception.UserAlreadyExistsException;
import com.erp.Coffee.model.User;
import com.erp.Coffee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/staff")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeesAdminController {

    private final UserService userService;

    @Autowired
    public EmployeesAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainPage(Model model) {
//        model.addAttribute("users", userService.findAll());
        model.addAttribute("users", userService.findAllBaristas());

        return "admin/staff/main_page";
    }

    @GetMapping("/add_user")
    public String createUserPage(@ModelAttribute("user") User user) {
        return "admin/staff/add_user";
    }

    @PostMapping("/add_user")
    public String createUser(User user) {
        try {
            userService.saveUser(user);

            return "redirect:/admin/staff";
        } catch (UserAlreadyExistsException e) {
            return "admin/staff/already_exists";
        }
    }

    @DeleteMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUserById(id);

        return "redirect:/admin/staff";
    }

    @GetMapping("/update_user/{id}")
    public String updateUserPage(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));

        return "admin/staff/update_user";
    }

    @PatchMapping("/update_user")
    public String updateUser(User user) {
        userService.updateUser(user);

        return "redirect:/admin/staff";
    }
}
