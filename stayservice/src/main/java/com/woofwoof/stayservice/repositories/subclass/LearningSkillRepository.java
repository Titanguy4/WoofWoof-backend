package com.woofwoof.stayservice.repositories.subclass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.models.subclass.LearningSkill;

import java.util.List;

@Repository
public interface LearningSkillRepository extends JpaRepository<LearningSkill, Long> {
    List<LearningSkill> findByStay_Id(Long id);
    
}