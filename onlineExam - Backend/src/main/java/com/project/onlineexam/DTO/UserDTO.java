package com.project.onlineexam.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.onlineexam.Entity.Enums.Role;
import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.Enums.Status;
import com.project.onlineexam.Entity.StudentExam;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {

    private int user_id;
    private Role role;
    private String first_name;
    private String last_name;
    private String email;
    private String password;
//    private Set<StudentExam> user_exam;
}
