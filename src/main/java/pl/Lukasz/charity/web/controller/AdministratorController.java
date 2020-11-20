package pl.Lukasz.charity.web.controller;

import pl.Lukasz.charity.entity.Role;
import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.service.RoleService;
import pl.Lukasz.charity.service.UserService;
import pl.Lukasz.charity.web.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Controller
@RequestMapping("/admin/admins")
public class AdministratorController {
    UserService userService;
    RoleService roleService;

    public AdministratorController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @ModelAttribute
    void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            model.addAttribute("LoggedUser", user);
        }
    }

    @GetMapping
    String getAdmins(Model model, Principal principal) {
        Role role = roleService.findById(1L);
        List<User> allUsers = role.getUsers();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getEmail().equals(principal.getName())) allUsers.remove(i);
        }
        model.addAttribute("users", allUsers);
        return "views/adminpart/admins/admin";
    }

    @GetMapping("/delete/{id}")
    String deleteAdmin(@PathVariable Long id,Principal principal) {
        if(userService.findUserByEmail(principal.getName()).getId().equals(id))return "redirect:/admin/admins";
        userService.saveDemoteAdmin(userService.findById(id));
        return "redirect:/admin/admins";
    }


    @GetMapping("/add")
    String addUser(Model model) {
        Role role = roleService.findById(2L);
        List<User> allUsers = role.getUsers();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getRole().size() == 1) users.add(allUsers.get(i));
        }
        model.addAttribute("users", users);
        return "views/adminpart/admins/add";
    }
    @GetMapping("/add/{id}")
    String addUserAction(@PathVariable Long id){
        User user = userService.findById(id);
        userService.saveAdmin(user);
        return "redirect:/admin/admins";
    }

    @GetMapping("/edit/{id}")
    String editAdmin(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "views/adminpart/admins/edit";
    }

    @PostMapping("/edit")
    String editAdminAction(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "views/adminpart/admins/edit";
        userService.saveEditUser(user);
        return "redirect:/admin/admins";
    }
}

