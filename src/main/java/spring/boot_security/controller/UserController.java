package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.boot_security.model.Person;
import spring.boot_security.security.SecurityUserService;
import spring.boot_security.service.PersonServiceImpl;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class UserController {
    private final SecurityUserService securityUserService;

    @Autowired
    public UserController(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }

    @GetMapping(value = "/user")
    public String showAllUser(Model model, Principal principal) {
        Optional<Person> person = securityUserService.findByUserName(principal.getName());
        model.addAttribute("person", person.get());
        return "user";
    }
}

