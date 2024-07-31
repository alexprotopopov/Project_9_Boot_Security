package spring.boot_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.boot_security.model.Person;
import spring.boot_security.model.Role;
import spring.boot_security.service.RoleService;
import spring.boot_security.service.PersonService;

import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private final RoleService roleService;
    private final PersonService personService;


    @Autowired
    public AdminController(PersonService personService, RoleService roleService) {
        this.personService = personService;
        this.roleService = roleService;
    }

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("person", personService.listUsers());
        return "admin";
    }

    @GetMapping(value = "/addNewUser")
    public String addNewUser(Model model) {
        model.addAttribute("person", new Person());
        List<Role> roles = roleService.listRole();
        model.addAttribute("allRoles", roles);
        return "user-info";
    }

    @PostMapping(value = "/saveUser")
    public String saveUser(@ModelAttribute("person") Person person) {
        personService.saveUser(person);
        return "redirect:/admin";
    }

    @GetMapping(value = "/updateUser")
    public String updateUser(@RequestParam("id") long id, Model model) {
        model.addAttribute("id", id);
        Person person = personService.getUser(id);
        model.addAttribute("person", person);
        List<Role> roles = roleService.listRole();
        model.addAttribute("allRoles", roles);
        return "user-info";
    }

    @GetMapping(value = "/deleteUser")
    public String deleteUser(@RequestParam("id") long id) {
        personService.deleteUser(id);
        return "admin";
    }
}
