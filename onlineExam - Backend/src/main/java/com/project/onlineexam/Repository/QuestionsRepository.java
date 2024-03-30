package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.QuestionType;
import com.project.onlineexam.Entity.Questions;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionsRepository extends JpaRepository<Questions,Integer>  {

    @Query(value="SELECT q FROM Questions q "+
            " WHERE q.question_type= :type AND" +
            " q.difficulty_level = :level ")
    public List<Questions> getQuestionsOnTypeAndLevel(@Param("type") QuestionType question_type,
                                              @Param("level") Difficulty_Level level);

    @Query(value = "SELECT COUNT(*) FROM Questions q WHERE q.question_type = :question_type")
    public int getQuestionCount(@Param("question_type") QuestionType question_type );


}
