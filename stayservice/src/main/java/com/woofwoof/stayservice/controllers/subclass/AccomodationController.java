package com.woofwoof.stayservice.entities.subclass;


import com.woofwoof.stayservice.models.subclass.Accomodation;
import com.woofwoof.stayservice.repositories.subclass.AccomodationRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;



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