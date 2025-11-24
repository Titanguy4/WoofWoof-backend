package com.woofwoof.stayservice.controllers.subclass;

import com.woofwoof.stayservice.models.subclass.LearningSkill;
import com.woofwoof.stayservice.repositories.subclass.LearningSkillRepository;

import org.springframework.web.bind.annotation.*;
import java.util.List;



@RestController
@RequestMapping("/learningskills")
public class LearningSkillController {

    private final LearningSkillRepository learningSkillRepository;

    public LearningSkillController(LearningSkillRepository learningSkillRepository) {
        this.learningSkillRepository = learningSkillRepository;
    }

    @GetMapping
    public List<LearningSkill> getAllLearningSkills() {
        return learningSkillRepository.findAll();
    }

    @GetMapping("/{id}")
    public LearningSkill getLearningSkillById(@PathVariable Long id) {
        return learningSkillRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("LearningSkill not found"));
    }

    @PostMapping
    public LearningSkill createLearningSkill(@RequestBody LearningSkill learningSkill) {
        if (learningSkill.getId() != null) {
            throw new IllegalArgumentException("New LearningSkill cannot already have an ID");
        }
        return learningSkillRepository.save(learningSkill);
    }

    @PutMapping("/{id}")
    public LearningSkill updateLearningSkill(@PathVariable Long id, @RequestBody LearningSkill learningSkill) {
        if (!learningSkillRepository.existsById(id)) {
            throw new IllegalArgumentException("LearningSkill not found");
        }
        learningSkill.setId(id);
        return learningSkillRepository.save(learningSkill);
    }

    @DeleteMapping("/{id}")
    public void deleteLearningSkill(@PathVariable Long id) {
        if (!learningSkillRepository.existsById(id)) {
            throw new IllegalArgumentException("LearningSkill not found");
        }
        learningSkillRepository.deleteById(id);
    }
}