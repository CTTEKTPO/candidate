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
    private final SexRepository sexRepository;
    private final CityRepository cityRepository;
    private final JobTitleRepository jobTitleRepository;
    private final StatusRepository statusRepository;

    private final CityService cityService;
    private final PersonalCardService personalCardService;
    private final SexService sexService;
    private final JobTitleService jobTitleService;
    private final StatusService statusService;


    public MainController(
            PersonalCardRepository personalCardRepository,
            SexRepository sexRepository,
            CityRepository cityRepository,
            JobTitleRepository jobTitleRepository,
            StatusRepository statusRepository,
            CityService cityService,
            PersonalCardService personalCardService,
            SexService sexService, JobTitleService jobTitleService, StatusService statusService) {
        this.personalCardRepository = personalCardRepository;
        this.sexRepository = sexRepository;
        this.cityRepository = cityRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.statusRepository = statusRepository;
        this.cityService = cityService;
        this.personalCardService = personalCardService;
        this.sexService = sexService;
        this.jobTitleService = jobTitleService;
        this.statusService = statusService;
    }

        @GetMapping("/")
        public String getAllPersonalCards(Model model) {
            List<PersonalCard> personalCards = personalCardService.getAll();
            model.addAttribute("personalCards", personalCards);
            return "job_seekers";
        }

        @GetMapping("/add-personal-card")
        public String showAddPersonalCardForm(Model model) {
//            List<Sex> sexes = sexRepository.findAll();
//            List<City> cities = cityRepository.findAll();
//            List<JobTitle> jobTitles = jobTitleRepository.findAll();
//            List<Status> statuses = statusRepository.findAll();

            model.addAttribute("personalCard", new PersonalCard());
            model.addAttribute("sex",new Sex());
            model.addAttribute("city", new City());
            model.addAttribute("jobTitle", new JobTitle());
            model.addAttribute("status", new Status());
//            model.addAttribute("sexes",sexes);
//            model.addAttribute("cities", cities);
//            model.addAttribute("jobTitles", jobTitles);
//            model.addAttribute("statuses", statuses);

            return "addendum";
        }

        @PostMapping("/add-personal-card")
        public String addPersonalCard(
                PersonalCard personalCard,
                Sex sex,
                City city,
                JobTitle jobTitle,
                Status status
                )

//                @ModelAttribute("personalCard") PersonalCard personalCard),
//                                      @ModelAttribute("sex")Sex sex,
//                                      @ModelAttribute("city")City city,
//                                      @ModelAttribute("citiesAdd") City addCity,
//                                      @ModelAttribute("jobTitles")JobTitle jobTitle,
//                                      @ModelAttribute("statuses")Status status)
//    @RequestParam("imageFile") MultipartFile imageFile) throws IOException
                                       {
//                                                           if (!imageFile.isEmpty()) {
//                    existingPersonalCard.setImagesBytes(imageFile.getBytes());
//                }

//            personalCard.setSex(sexRepository.findById(personalCard.getSex().getId()).orElse(null));
//            personalCard.setCity(cityRepository.findById(personalCard.getCity().getId()).orElse(null));
//            personalCard.setJobTitle(jobTitleRepository.findById(personalCard.getJobTitle().getId()).orElse(null));
//            personalCard.setStatus(statusRepository.findById(personalCard.getStatus().getId()).orElse(null));
//            personalCard.setDateOfBirth(personalCard.getDateOfBirth());

            personalCardService.saveOrUpdate(personalCard);
            sexService.saveOrUpdate(sex);
            cityService.saveOrUpdate(city);
            jobTitleService.saveOrUpdate(jobTitle);
            statusService.saveOrUpdate(status);

            return "redirect:/";
        }

        //ДОПИСАТЬ РЕДИРЕКТ НА ПОСТ ЗАПРОС, КОТОРЫЙ ВНАЧАЛЕ ДОБАВЛЯЕТ ИЛИ ПРОВЕРЯЕТ ДАННЫЕ В ДРУГИХ ТАБЛИЦАХ
        // ПОСЛЕ НАЖАТИЯ НА КНОПКУ ДОБАВИТЬ НА СТРАНИЦЕ ADDENDUM
        //ПОТОМ ВЫЗЫВАЕТСЯ @PostMapping("/add-personal-card") ДЛЯ ДОБАВЛЕНИЯ В ОСНОВНУЮ ТАБЛ.

        @GetMapping("/edit-personal-card/{id}")
        public String showEditPersonalCardForm(@PathVariable("id") Long id, Model model) {
            System.out.println("In get " + id);
            Optional<PersonalCard> optionalPersonalCard = personalCardRepository.findById(id);
            List<Sex> sexes = sexRepository.findAll();
            List<City> cities = cityRepository.findAll();
            List<JobTitle> jobTitles = jobTitleRepository.findAll();
            List<Status> statuses = statusRepository.findAll();

            if (optionalPersonalCard.isPresent()) {
                //PersonalCard personalCard = personalCardService.getById(id);
                PersonalCard personalCard = optionalPersonalCard.get();
                model.addAttribute("personalCard", personalCard);
                model.addAttribute("sexes", sexes);
                model.addAttribute("cities", cities);
                model.addAttribute("jobTitles", jobTitles);
                model.addAttribute("statuses", statuses);
                return "editing";
            } else {
                return "redirect:/";
            }
        }

        @PostMapping("/edit-personal-card/{id}")
        public String editPersonalCard(@PathVariable("id") Long id, @ModelAttribute("personalCard") PersonalCard updatedPersonalCard){
//                                       @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
            Optional<PersonalCard> optionalPersonalCard = personalCardRepository.findById(id);
            if (optionalPersonalCard.isPresent()) {
                PersonalCard existingPersonalCard = optionalPersonalCard.get();
                existingPersonalCard.setFullName(updatedPersonalCard.getFullName());
                existingPersonalCard.setDateOfBirth(updatedPersonalCard.getDateOfBirth());
                existingPersonalCard.setAge(updatedPersonalCard.getAge());
                existingPersonalCard.setSalary(updatedPersonalCard.getSalary());
                existingPersonalCard.setPhone(updatedPersonalCard.getPhone());
                existingPersonalCard.setExperience(updatedPersonalCard.getExperience());
                existingPersonalCard.setEducation(updatedPersonalCard.getEducation());
                existingPersonalCard.setSkills(updatedPersonalCard.getSkills());
                existingPersonalCard.setComments(updatedPersonalCard.getComments());
                existingPersonalCard.setSex(sexRepository.findById(updatedPersonalCard.getSex().getId()).orElse(null));
                existingPersonalCard.setCity(cityRepository.findById(updatedPersonalCard.getCity().getId()).orElse(null));
                existingPersonalCard.setJobTitle(jobTitleRepository.findById(updatedPersonalCard.getJobTitle().getId()).orElse(null));
                existingPersonalCard.setStatus(statusRepository.findById(updatedPersonalCard.getStatus().getId()).orElse(null));

//                if (!imageFile.isEmpty()) {
//                    existingPersonalCard.setImagesBytes(imageFile.getBytes());
//                }

                personalCardRepository.save(existingPersonalCard);
            }
            return "redirect:/";
        }

        @GetMapping("/delete-personal-card/{id}")
        public String deletePersonalCard(@PathVariable("id") Long id) {
            personalCardRepository.deleteById(id);
            return "redirect:/";
        }
    }




