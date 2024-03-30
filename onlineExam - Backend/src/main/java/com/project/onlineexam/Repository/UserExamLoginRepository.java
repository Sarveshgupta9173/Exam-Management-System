package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.UserExamLogin;
import com.project.onlineexam.Entity.UserExamSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExamLoginRepository extends JpaRepository<UserExamLogin,Integer> {

    @Query(value = "select s from UserExamLogin s where " +
            "s.student.user_id = :student_id and s.exam.exam_id=:exam_id " )
    public UserExamLogin getByStudentIdExamId(@Param("student_id") int student_id,
                                                         @Param("exam_id") int exam_id);
}
