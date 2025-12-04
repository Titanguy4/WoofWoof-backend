package com.woofwoof.stayservice.controllers.subclass;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.woofwoof.stayservice.entities.subclass.Accomodation;
import com.woofwoof.stayservice.repositories.subclass.AccomodationRepository;

@RestController
@RequestMapping("/accomodations")
public class AccomodationController {

    private final AccomodationRepository accomodationRepository;

    public AccomodationController(AccomodationRepository accomodationRepository) {
        this.accomodationRepository = accomodationRepository;
    }

    @GetMapping
    public List<Accomodation> getAllAccomodations() {
        return accomodationRepository.findAll();
    }

    @GetMapping("/{id}")
    public Accomodation getAccomodationById(@PathVariable Long id) {
        return accomodationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Accomodation not found"));
    }

    @PostMapping
    public Accomodation createAccomodation(@RequestBody Accomodation accomodation) {
        if (accomodation.getId() != null) {
            throw new IllegalArgumentException("New Accomodation cannot already have an ID");
        }
        return accomodationRepository.save(accomodation);
    }

    @PutMapping("/{id}")
    public Accomodation updateAccomodation(@PathVariable Long id, @RequestBody Accomodation accomodation) {
        if (!accomodationRepository.existsById(id)) {
            throw new IllegalArgumentException("Accomodation not found");
        }
        accomodation.setId(id);
        return accomodationRepository.save(accomodation);
    }

    @DeleteMapping("/{id}")
    public void deleteAccomodation(@PathVariable Long id) {
        if (!accomodationRepository.existsById(id)) {
            throw new IllegalArgumentException("Accomodation not found");
        }
        accomodationRepository.deleteById(id);
    }
}