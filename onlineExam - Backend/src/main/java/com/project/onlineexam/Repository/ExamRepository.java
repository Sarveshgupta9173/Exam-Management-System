package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ExamRepository extends JpaRepository<Exam,Integer> {
}
