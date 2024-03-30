package com.project.onlineexam.DTO;

import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.Questions;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuestionExamDTO {
    private int id;
    private ExamDTO exam;
    private QuestionsDTO question;

}
