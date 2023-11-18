package com.example.candidate.controller;

import com.example.candidate.service.*;
import org.springframework.ui.Model;
import com.example.candidate.model.*;
import com.example.candidate.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class MainController {
/*
    @Autowired
    CityService cityService;

    @Autowired
    JobTitleService jobTitleService;

    @Autowired
    PersonalCardService personalCardService;

    @Autowired
    SexService sexService;

    @Autowired
    StatusService statusService;

    @GetMapping({"/","/job_seekers"})
    public String viewMainPage(){

        return "job_seekers";
    }*/


    private final PersonalCardRepository personalCardRepository;
    private final CityRepository cityRepository;
    private final JobTitleRepository jobTitleRepository;
    private final StatusRepository statusRepository;

    private final CityService cityService;
    private final PersonalCardService personalCardService;
    private final JobTitleService jobTitleService;
    private final StatusService statusService;


    public MainController(
            PersonalCardRepository personalCardRepository,
            CityRepository cityRepository,
            JobTitleRepository jobTitleRepository,
            StatusRepository statusRepository,
            CityService cityService,
            PersonalCardService personalCardService,
            JobTitleService jobTitleService, StatusService statusService) {
        this.personalCardRepository = personalCardRepository;
        this.cityRepository = cityRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.statusRepository = statusRepository;
        this.cityService = cityService;
        this.personalCardService = personalCardService;
        this.jobTitleService = jobTitleService;
        this.statusService = statusService;
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

            PersonalCard personalCard = personalCardService.getById(id);

            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
            model.addAttribute("editMode", true);  // Установка атрибута editMode для выбора картинки (ред или доб)
            model.addAttribute("personalCard", personalCard);
            model.addAttribute("city", cities);
            model.addAttribute("jobTitle", jobTitles);
            model.addAttribute("status", statuses);
            return "addendum";

        }

        @GetMapping("/delete-personal-card/{id}")
        public String deletePersonalCard(@PathVariable("id") Long id) {
            personalCardService.deleteById(id);
            return "redirect:/";
        }
    }




