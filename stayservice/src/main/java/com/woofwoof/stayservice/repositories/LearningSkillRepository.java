package com.woofwoof.stayservice.repositories;

import com.woofwoof.stayservice.models.LearningSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LearningSkillRepository extends JpaRepository<LearningSkill, Long> {
    List<LearningSkill> findByStay_Id(Long id);
}