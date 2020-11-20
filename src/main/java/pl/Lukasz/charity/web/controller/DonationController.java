package pl.Lukasz.charity.web.controller;

import pl.Lukasz.charity.entity.Donation;
import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.service.CategoryService;
import pl.Lukasz.charity.service.DonationService;
import pl.Lukasz.charity.service.InstitutionService;
import pl.Lukasz.charity.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/donate")
public class DonationController {

    DonationService donationService;
    InstitutionService institutionService;
    CategoryService categoryService;
    UserService userService;

    public DonationController(DonationService donationService, InstitutionService institutionService, CategoryService categoryService, UserService userService) {
        this.donationService = donationService;
        this.institutionService = institutionService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    String donateForm(Model model) {
        model.addAttribute("donation", new Donation());
        model.addAttribute("categoriesFromServlet", categoryService.findAll());
        model.addAttribute("institutions", institutionService.findAll());
        return "views/userpart/donate";
    }

    @PostMapping
    String donateFormAction(@Valid @ModelAttribute Donation donation, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) return "views/userpart/donate";
        donation.setUser(userService.findUserByEmail(principal.getName()));
        donationService.save(donation);
        return "views/userpart/donationConfimation";
    }

    @ModelAttribute
    void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            model.addAttribute("LoggedUser", user);
        }
    }

}

