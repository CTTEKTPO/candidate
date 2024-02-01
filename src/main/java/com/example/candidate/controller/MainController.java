package com.example.candidate.controller;

import com.example.candidate.service.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import com.example.candidate.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;


@Controller
public class MainController {

    private final CityService cityService;
    private final PersonalCardService personalCardService;
    private final JobTitleService jobTitleService;
    private final StatusService statusService;
    private final UserService userService;


    public MainController(
            CityService cityService,
            PersonalCardService personalCardService,
            JobTitleService jobTitleService,
            StatusService statusService, UserService userService) {
        this.cityService = cityService;
        this.personalCardService = personalCardService;
        this.jobTitleService = jobTitleService;
        this.statusService = statusService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String getAllPersonalCards(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        Optional<? extends GrantedAuthority> first = userDetails.getAuthorities().stream().findFirst();
        if (first.isPresent()) {
            if (first.get().getAuthority().equals("ROLE_full")) {
                model.addAttribute("show_admin", true);
            }
            else {
                model.addAttribute("show_admin", false);
            }
        }

        List<PersonalCard> sortedPersonalCards = personalCardService.getAll().stream()
                .sorted(Comparator.comparing(PersonalCard::getId))
                .collect(Collectors.toList());

        model.addAttribute("personalCards", sortedPersonalCards);
        return "job_seekers";
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/filteredPersonalCards")
    public String getFilteredPersonalCards(
            @RequestParam Map<String, String> params,
            Model model) {

        List<PersonalCard> personalCards = personalCardService.getAll().stream()
                .sorted(Comparator.comparing(PersonalCard::getId))
                .collect(Collectors.toList());
        // Если параметры отсутствуют, вернуть весь список
        if (params.isEmpty()) {
            model.addAttribute("personalCards", personalCards);
            return "job_seekers";
        }

        Filter filter = new Filter();

        Set<PersonalCard> uniquePersonalCards = new HashSet<>(personalCards);

        Map<String, String> NewParams = params.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> Filter.FieldMapper.mapField(entry.getKey()),
                        Map.Entry::getValue
                ));

        List<PersonalCard> filteredPersonalCards = filter.filter(new ArrayList<>(uniquePersonalCards), NewParams);

        filteredPersonalCards = filteredPersonalCards.stream()
                .sorted(Comparator.comparing(PersonalCard::getId))
                .collect(Collectors.toList());

        model.addAttribute("personalCards", filteredPersonalCards);
        return "job_seekers";
    }


    @GetMapping("/newCandidate")
        public String showAddPersonalCardForm(Model model) {
            List<City> cities = cityService.getAll();
            List<JobTitle> jobTitles = jobTitleService.getAll();
            List<Status> statuses = statusService.getAll();

            model.addAttribute("pageTitle", "Add New Candidate");
            model.addAttribute("personalCard", new PersonalCard());
            model.addAttribute("city", cities);
            model.addAttribute("jobTitle", jobTitles);
            model.addAttribute("status", statuses);

            return "addendum";
        }

        @PostMapping("/saveCandidate")
        public String addPersonalCard(PersonalCard personalCard){
            personalCardService.saveOrUpdate(personalCard);
            return "redirect:/";
        }


        @GetMapping("/edit-personal-card/{id}")
        public String showEditPersonalCardForm(@PathVariable("id") Long id, Model model) {
            List<City> cities = cityService.getAll();
            List<JobTitle> jobTitles = jobTitleService.getAll();
            List<Status> statuses = statusService.getAll();

            try {
                PersonalCard personalCard = personalCardService.getById(id);
                model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
                model.addAttribute("editMode", true);  // Установка атрибута editMode для выбора картинки (ред или доб)
                model.addAttribute("personalCard", personalCard);
                model.addAttribute("city", cities);
                model.addAttribute("jobTitle", jobTitles);
                model.addAttribute("status", statuses);
                return "addendum";
            }catch (Exception e){
                return "redirect:/";
            }
        }

        @GetMapping("/delete-personal-card/{id}")
        public String deletePersonalCard(@PathVariable("id") Long id) {
            personalCardService.deleteById(id);
            return "redirect:/";
        }

    @GetMapping("/median")
    public String showMedianPage(Model model) {
        List<PersonalCard> personalCards = personalCardService.getAll();
        List<JobTitle> jobTitles = jobTitleService.getAll();
        model.addAttribute("personalCards", personalCards);
        model.addAttribute("jobTitles", jobTitles);
        return "median";
    }

    @GetMapping("/admin_panel")
    public String getAdminPanel(Model model) {
        List<City> cities = cityService.getAll().stream()
                .sorted(Comparator.comparing(City::getId))
                .collect(Collectors.toList());

        List<JobTitle> jobTitles = jobTitleService.getAll().stream()
                .sorted(Comparator.comparing(JobTitle::getId))
                .collect(Collectors.toList());

        List<Status> statuses = statusService.getAll().stream()
                .sorted(Comparator.comparing(Status::getId))
                .collect(Collectors.toList());

        List<User> users = userService.getAll().stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());

        model.addAttribute("city", cities);
        model.addAttribute("jobTitle", jobTitles);
        model.addAttribute("status", statuses);
        model.addAttribute("user", users);

        return "admin_panel";
    }
    @PostMapping(value = "/save/city")
    public String addCity(City city){
        cityService.saveOrUpdate(city);
        return "redirect:/admin_panel";
    }
    @PostMapping("/save/jobTitle")
    public String addJobTitle(JobTitle jobTitle){
        jobTitleService.saveOrUpdate(jobTitle);
        return "redirect:/admin_panel";
    }
    @PostMapping("/save/status")
    public String addStatus(Status status){
        statusService.saveOrUpdate(status);
        return "redirect:/admin_panel";
    }
    @PostMapping("/save/user")
    public String addUser(User user, @RequestParam String old_authority) {
        userService.saveOrUpdate(user, old_authority != null && !user.getAuthority().equals(old_authority));
        return "redirect:/admin_panel";
    }
    @PostMapping("/save/user/activated")
    public String activatedUser(User user, RedirectAttributes redirectAttributes) {
        if(!userService.activatedUser(user))
            redirectAttributes.addFlashAttribute("errorMessage", "Нельзя отключить единственного пользователя с полными правами.");
        return "redirect:/admin_panel";
    }

    @GetMapping("/delete/city/{id}")
    public String deleteCity(@PathVariable("id") Long id) {
        cityService.deleteById(id);
        return "redirect:/admin_panel";
    }
    @GetMapping("/delete/jobTitle/{id}")
    public String deleteJobTitle(@PathVariable("id") Long id) {
        jobTitleService.deleteById(id);
        return "redirect:/admin_panel";
    }
    @GetMapping("/delete/status/{id}")
    public String deleteStatus(@PathVariable("id") Long id) {
        statusService.deleteById(id);
        return "redirect:/admin_panel";
    }
    @GetMapping("/delete/user/{id}")
    public String deleteUser(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (userService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("successMessage", "Пользователь успешно удален");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Нельзя удалить единственного пользователя с полными правами.");
        }
        return "redirect:/admin_panel";
    }

}




