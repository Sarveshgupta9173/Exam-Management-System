package com.project.onlineexam.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.onlineexam.Entity.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "student_exam")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentExam {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student",nullable = false,referencedColumnName = "user_id")
    private User student;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam",nullable = false,referencedColumnName = "exam_id")
    private Exam exam;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column
    private int total_marks_scored;


}
