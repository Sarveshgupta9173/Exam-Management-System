package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.QuestionExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionExamRepository extends JpaRepository<QuestionExam,Integer> {

    @Query(value = "Select qe from QuestionExam qe where qe.exam.exam_id = :exam_id")
    public List<QuestionExam> getAllQuestionsByExamId(@Param("exam_id") int exam_id);
}
