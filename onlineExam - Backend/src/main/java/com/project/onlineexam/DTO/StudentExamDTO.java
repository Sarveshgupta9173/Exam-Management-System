package com.project.onlineexam.DTO;

import com.project.onlineexam.Entity.Enums.Status;
import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.User;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StudentExamDTO {

    private int id;
    private UserDTO student;
    private ExamDTO exam;
    private Status status;
    private int total_marks_scored;
}
