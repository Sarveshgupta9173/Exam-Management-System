package com.project.onlineexam.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Questions;
import com.project.onlineexam.Entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamDTO {
    private int exam_id;
    private int no_of_mcq_questions;
    private int no_of_programming_questions;
    private int pass_marks;
    private Difficulty_Level difficulty_level;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start_time;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end_time;

    private double duration;

//    private Set<Questions> questions;
//    private Set<User> students;
}
