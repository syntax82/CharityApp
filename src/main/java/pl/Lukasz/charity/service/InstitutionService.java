package pl.Lukasz.charity.service;

import pl.Lukasz.charity.entity.Institution;

import java.util.List;

public interface InstitutionService {
    List<Institution> findAll();
    void deleteById(Long id);
    Institution save(Institution institution);
    Institution findById(Long id);
}
