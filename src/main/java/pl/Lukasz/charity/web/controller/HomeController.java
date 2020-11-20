package pl.Lukasz.charity.web.controller;

import pl.Lukasz.charity.entity.Donation;
import pl.Lukasz.charity.entity.Institution;
import pl.Lukasz.charity.entity.User;
import pl.Lukasz.charity.service.DonationService;
import pl.Lukasz.charity.service.InstitutionService;
import pl.Lukasz.charity.service.UserService;
import pl.Lukasz.charity.web.dto.PasswordDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    UserService userService;
    InstitutionService institutionService;
    DonationService donationService;
    BCryptPasswordEncoder passwordEncoder;

    public HomeController(UserService userService, InstitutionService institutionService, DonationService donationService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.institutionService = institutionService;
        this.donationService = donationService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    String getHomePage(Model model, Principal principal) {
        System.out.println();
        return "views/userpart/index";
    }

    @ModelAttribute
    void addInstitution(Model model) {
        List<Institution> institutions = institutionService.findAll();
        List<List<Institution>> pairs = new ArrayList<>();
        for (int i = 0; i < institutions.size(); i += 2) {
            List<Institution> pair = new ArrayList<>();
            pair.add(institutions.get(i));
            if ((i + 1) < institutions.size()) pair.add(institutions.get(i + 1));
            pairs.add(pair);
        }
        model.addAttribute("pairsInstitution", pairs);
    }

    @ModelAttribute
    void addDonations(Model model) {
        List<Donation> donations = donationService.findAll();
        int generalQuantity = donations.stream().mapToInt(Donation::getQuantity).sum();
        model.addAttribute("quantityOfDonation", donations.size());
        model.addAttribute("quantityOfPacks", generalQuantity);
    }

    @ModelAttribute
    void addLoggedUser(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.findUserByEmail(principal.getName());
            model.addAttribute("LoggedUser", user);
        }
    }

    @GetMapping("/details")
    String getLoggedUserDetails(){
        return "views/userpart/details";
    }

    @GetMapping("/details/edit")
    String editLoggedUser(Model model, Principal principal){
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("user", user);
        return "views/userpart/edit";
    }
    @PostMapping("/details/edit")
    String EditLoggedUserAction(@Valid User user, BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()) return "views/userpart/edit";
        User userLogged = userService.findUserByEmail(principal.getName());
        userLogged.setName(user.getName());
        userLogged.setSurname(user.getSurname());
        userLogged.setEmail(user.getEmail());
        userService.save(userLogged);
        return "views/userpart/edit";
    }



    @GetMapping("/details/editpass")
    String editLoggedUserPass(Model model){
        model.addAttribute("passwordDto", new PasswordDto());
        return "views/userpart/editpass";
    }

    @PostMapping("/details/editpass")
    String editLoggedUserPassAction(@Valid PasswordDto passwordDto, BindingResult bindingResult, Model model, Principal principal){
        if(bindingResult.hasErrors()) return "views/userpart/editpass";
        if(!passwordDto.getPass1().equals(passwordDto.getPass2())){
            model.addAttribute("msg", true);
            return "views/userpart/editpass";
        }
        User user = userService.findUserByEmail(principal.getName());
        user.setPassword(passwordEncoder.encode(passwordDto.getPass1()));
        userService.save(user);
        return "redirect:/details";
    }


}
