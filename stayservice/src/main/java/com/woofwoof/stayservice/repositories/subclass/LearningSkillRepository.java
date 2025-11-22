package com.woofwoof.stayservice.repositories.subclass;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.woofwoof.stayservice.entities.subclass.LearningSkill;

@Repository
public interface LearningSkillRepository extends JpaRepository<LearningSkill, Long> {
    List<LearningSkill> findByStay_Id(Long id);

}