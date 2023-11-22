package com.example.candidate.controller;

import com.example.candidate.service.*;
import org.springframework.ui.Model;
import com.example.candidate.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainController {

    private final CityService cityService;
    private final PersonalCardService personalCardService;
    private final JobTitleService jobTitleService;
    private final StatusService statusService;
    private final FilterService filterService;


    public MainController(
            CityService cityService,
            PersonalCardService personalCardService,
            JobTitleService jobTitleService,
            StatusService statusService, FilterService filterService) {
        this.cityService = cityService;
        this.personalCardService = personalCardService;
        this.jobTitleService = jobTitleService;
        this.statusService = statusService;
        this.filterService = filterService;
    }

        @GetMapping("/")
        public String getAllPersonalCards(Model model) {
            List<PersonalCard> personalCards = personalCardService.getAll();
            model.addAttribute("personalCards", personalCards);
            return "job_seekers";
        }

        @GetMapping("/newCandidate")
        public String showAddPersonalCardForm(Model model) {
            List<City> cities = cityService.getAll();
            List<JobTitle> jobTitles = jobTitleService.getAll();
            List<Status> statuses = statusService.getAll();

            model.addAttribute("pageTitle", "Add New User");
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
        public String showMedianPage(Model model){
            List<PersonalCard> personalCards = personalCardService.getAll();
            List<JobTitle> jobTitles = jobTitleService.getAll();
            model.addAttribute("personalCard", personalCards);
            model.addAttribute("jobTitles", jobTitles);
            return "median";
        }


    }




