package com.project.onlineexam.DTO;

import com.project.onlineexam.Entity.Enums.Answer;
import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.Questions;
import com.project.onlineexam.Entity.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserExamSubmissionDTO {

    private int user_exam_submission_id;
    private UserDTO student;
    private ExamDTO exam;
    private QuestionsDTO question;
    private String answer;
    private int marks_obtained;
}
