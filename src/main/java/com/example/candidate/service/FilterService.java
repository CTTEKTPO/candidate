package com.example.candidate.service;

import com.example.candidate.model.Filter;
import com.example.candidate.model.PersonalCard;
import com.example.candidate.repository.FilterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService {

    private final FilterRepository filterRepository;

    @Autowired
    public FilterService(FilterRepository filterRepository) {
        this.filterRepository = filterRepository;
    }

    public List<Filter> getAllFilters() {
        return filterRepository.findAll();
    }
    public Filter getById(Long id) {
        Optional<Filter> result = filterRepository.findById(id);
        return result.orElse(null);
    }
    public Filter saveFilter(Filter filterEntity) {
        return filterRepository.save(filterEntity);
    }

    public void deleteFilter(Long filterId) {
        filterRepository.deleteById(filterId);
    }
}
