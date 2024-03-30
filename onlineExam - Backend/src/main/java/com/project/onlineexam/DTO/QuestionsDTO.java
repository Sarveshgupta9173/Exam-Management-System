package com.project.onlineexam.DTO;

import com.project.onlineexam.Entity.Enums.Answer;
import com.project.onlineexam.Entity.Enums.Categories;
import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.QuestionType;
import com.project.onlineexam.Entity.Exam;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuestionsDTO {
    private int question_id;
    private String question;
    private QuestionType question_type;
    private  String option_A;
    private  String option_B;
    private  String option_C;
    private  String option_D;
    private Answer answer;
    private Difficulty_Level difficulty_level;
    private Categories categories;


}
