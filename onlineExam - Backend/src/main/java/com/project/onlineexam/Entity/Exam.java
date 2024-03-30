package com.project.onlineexam.Entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.Status;
import lombok.*;
import org.springframework.context.annotation.DependsOn;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "exam")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "exam_id")
@AllArgsConstructor
@NoArgsConstructor
public class Exam{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int exam_id;

    @Column(nullable = false)
    private int no_of_mcq_questions;

    @Column(nullable = false)
    private int no_of_programming_questions;

    @Column(nullable = false)
    private int pass_marks;

    @Column(nullable = false)
    private double duration;

    @Enumerated(EnumType.STRING)
    private Difficulty_Level difficulty_level;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime start_time;

    @Column
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime end_time;

    @OneToMany(mappedBy = "exam")
    private Set<QuestionExam> question_exam;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "exam",cascade = CascadeType.ALL)
    private Set<StudentExam> student_exam;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "exam",cascade = CascadeType.ALL)
    private Set<UserExamSubmission> user_exam_submission;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "exam",cascade = CascadeType.ALL)
    private Set<UserExamLogin> user_exam_login;




    public Exam(int exam_id, int no_of_mcq_questions, int no_of_programming_questions, int pass_marks, int duration, LocalDateTime start_time, LocalDateTime end_time) {
        this.exam_id = exam_id;
        this.no_of_mcq_questions = no_of_mcq_questions;
        this.no_of_programming_questions = no_of_programming_questions;
        this.pass_marks = pass_marks;
        this.duration = duration;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
