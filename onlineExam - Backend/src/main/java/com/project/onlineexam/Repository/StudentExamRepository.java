package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.Enums.Status;
import com.project.onlineexam.Entity.StudentExam;
import com.project.onlineexam.Entity.UserExamLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam,Integer> {

    @Query(value = "Select se from StudentExam se where se.status= :status")
    public List<StudentExam> getAllUsersByStatus(@Param("status") Status status);

    @Query(value = "Select se from StudentExam se where se.student.user_id= :student_id")
    public List<StudentExam> getStudentExamDetailsByStudentId(@Param("student_id") int student_id);

    @Query(value = "select se from StudentExam se where " +
            "se.student.user_id = :student_id and se.exam.exam_id=:exam_id " )
    public StudentExam getByStudentIdExamId(@Param("student_id") int student_id,
                                              @Param("exam_id") int exam_id);
}
