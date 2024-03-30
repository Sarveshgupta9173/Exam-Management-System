package com.project.onlineexam.Entity;

import com.fasterxml.jackson.annotation.*;
import com.project.onlineexam.Entity.Enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "user_id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentExam> student_exam;

    @JsonIgnore
    @JsonBackReference()
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<UserExamSubmission> user_exam_submission;

    @JsonIgnore
    @JsonBackReference()
    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL)
    private Set<UserExamLogin> user_exam_login;

}
