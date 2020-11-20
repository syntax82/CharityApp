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
@RequestMapping("/admin/users")
public class UsersController {
    UserService userService;
    RoleService roleService;

    public UsersController(UserService userService, RoleService roleService) {
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
    String getUsers(Model model) {
        Role role = roleService.findById(2L);
        List<User> allUsers = role.getUsers();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < allUsers.size(); i++) {
            if (allUsers.get(i).getRole().size() == 1) users.add(allUsers.get(i));
        }
        model.addAttribute("users", users);
        return "views/adminpart/users/user";
    }

    @GetMapping("/delete/{id}")
    String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/block/{id}")
    String blockUser(@PathVariable Long id) {
        User user = userService.findById(id);
        user.setIsBlocked(!user.getIsBlocked());
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/add")
    String addUser(Model model){
        model.addAttribute("userDto", new UserDto());
        return "views/adminpart/users/add";
    }

    @PostMapping("/add")
    String addUserAction(@Valid UserDto userDto, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            if (!userDto.getPassword1().equals(userDto.getPassword2())) model.addAttribute("msg", true);
            return "views/adminpart/users/add";
        }
        if (!userDto.getPassword1().equals(userDto.getPassword2())) {
            model.addAttribute("msg", true);
            return "views/adminpart/users/add";
        }
        userService.save(userDto);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    String editUser(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "views/adminpart/users/edit";
    }

    @PostMapping("/edit")
    String editUserAction(@Valid User user, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "views/adminpart/users/edit";
        userService.saveEditUser(user);
        return "redirect:/admin/users";
    }
}

