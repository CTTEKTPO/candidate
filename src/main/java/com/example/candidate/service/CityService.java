package com.example.candidate.service;

import com.example.candidate.model.City;
import com.example.candidate.model.PersonalCard;
import com.example.candidate.model.Status;
import com.example.candidate.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    final
    CityRepository repo;

    public CityService(CityRepository repo) {
        this.repo = repo;
    }

    public List<City> getAll(){
        return repo.findAll();
    }

    public City getById(Long id) {
        Optional<City> optionalCity = repo.findById(id);
        return optionalCity.orElse(null);
    }

    public void saveOrUpdate(City city) {
        if (city.getId() == null) {
            // Если id отсутствует, значит, это новая запись
            repo.save(city);
        } else {
            Optional<City> optionalCity = repo.findById(city.getId());
            if (optionalCity.isPresent()) {
                City existingCity = optionalCity.get();
                existingCity.setName(city.getName());
                repo.save(city);
            }
        }
    }

    public boolean deleteById(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            // needless
        }

        return repo.findById(id).isEmpty();
    }

    /*public void addNewCity(City city){
        Optional<City> cityOptional = repo.findCityByName(city.getName());
        if(cityOptional.isPresent()) throw new IllegalStateException("name taken");
        repo.save(city);
    }*/
}
    