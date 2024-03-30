package com.project.onlineexam.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.onlineexam.Entity.Enums.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "user_exam_submission")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_exam_submission_id")
public class UserExamSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_exam_submission_id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student",nullable = false,referencedColumnName = "user_id")
    private User student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam",nullable = false,referencedColumnName = "exam_id")
    private Exam exam;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question",nullable = false,referencedColumnName = "question_id")
    private Questions question;

    @Column
    @Lob
    private String answer;

    @Column(nullable = true)
    private int marks_obtained;

}
