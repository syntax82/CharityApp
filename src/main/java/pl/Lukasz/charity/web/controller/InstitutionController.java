package pl.Lukasz.charity.web.controller;

import pl.Lukasz.charity.entity.Institution;
import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.service.InstitutionService;
import pl.Lukasz.charity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin/institutions")
public class InstitutionController {
    InstitutionService institutionService;
    UserService userService;

    public InstitutionController(InstitutionService institutionService, UserService userService) {
        this.institutionService = institutionService;
        this.userService = userService;
    }

    @ModelAttribute
    void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            model.addAttribute("LoggedUser", user);
        }
    }

    @GetMapping
    String getAllInstitution(Model model){
        List<Institution> institutions = institutionService.findAll();
        model.addAttribute("institutions",institutions);
        return "views/adminpart/institution/institution";
    }


    @GetMapping("/delete/{id}")
    String deleteInstitution(@PathVariable Long id){
        institutionService.deleteById(id);
        return "redirect:/admin/institutions";
    }

    @GetMapping("/add")
    String addInstitutionForm(Model model){
        model.addAttribute("institution", new Institution());
        return "views/adminpart/institution/add";
    }


    @PostMapping("/add")
    String addInstitutionAction(@Valid Institution institution, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "views/adminpart/institution/add";
        }
        institutionService.save(institution);
        return "redirect:/admin/institutions";
    }

    @GetMapping("/edit/{id}")
    String editInstitution(@PathVariable Long id, Model model){
        Institution institution = institutionService.findById(id);
        model.addAttribute("institution",institution);
        return "views/adminpart/institution/edit";
    }

    @PostMapping("/edit")
    String editInstitutionAction(@Valid Institution institution, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "views/adminpart/institution/edit";
        }
        institutionService.save(institution);
        return "redirect:/admin/institutions";
    }

}
