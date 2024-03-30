package com.project.onlineexam.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.project.onlineexam.Entity.Enums.Answer;
import com.project.onlineexam.Entity.Enums.Categories;
import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "questions")
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "question_id")
public class Questions implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int question_id;

    @Enumerated(EnumType.STRING)
    private QuestionType question_type;

    @Column(nullable = false)
    private String question;

    @Column
    private  String option_A;

    @Column
    private  String option_B;

    @Column
    private  String option_C;

    @Column
    private  String option_D;

    @Enumerated(EnumType.STRING)
    private Answer answer;

    @Enumerated(EnumType.STRING)
    private Difficulty_Level difficulty_level;

    @Enumerated(EnumType.STRING)
    private Categories categories;

//    @JsonIgnore
//    @JsonBackReference
    @OneToMany(mappedBy = "question")
    private Set<QuestionExam> question_exam;

    @JsonIgnore
    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    private Set<UserExamSubmission> user_exam_submission;

    public Questions(int question_id, QuestionType questionType, String question, String option_A, String option_B, String option_C, String option_D, Answer answer, Difficulty_Level difficulty_level, Categories categories) {
        this.question_id = question_id;
        this.question_type = questionType;
        this.question = question;
        this.option_A = option_A;
        this.option_B = option_B;
        this.option_C = option_C;
        this.option_D = option_D;
        this.answer = answer;
        this.difficulty_level = difficulty_level;
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "question_id=" + question_id +
                ", questionType=" + question_type +
                ", question='" + question + '\'' +
                ", option_A='" + option_A + '\'' +
                ", option_B='" + option_B + '\'' +
                ", option_C='" + option_C + '\'' +
                ", option_D='" + option_D + '\'' +
                ", answer=" + answer +
                ", difficulty_level=" + difficulty_level +
                ", categories=" + categories +
                '}';
    }
}
