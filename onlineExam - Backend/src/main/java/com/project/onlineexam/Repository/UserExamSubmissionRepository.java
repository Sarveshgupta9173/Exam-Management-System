package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.UserExamSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserExamSubmissionRepository extends JpaRepository<UserExamSubmission,Integer> {
    @Query(value = "select s from UserExamSubmission s where " +
            "s.student.user_id = :student_id and s.exam.exam_id=:exam_id and " +
            "s.question.question_id = :question_id")
    public UserExamSubmission getByStudentIdExamIdQuestionId(@Param("student_id") int student_id,
                                                             @Param("exam_id") int exam_id,
                                                             @Param("question_id") int question_id);

    @Query(value = "select s from UserExamSubmission s where " +
            "s.student.user_id = :student_id and s.exam.exam_id=:exam_id " )
    public List<UserExamSubmission> getByStudentIdExamId(@Param("student_id") int student_id,
                                                         @Param("exam_id") int exam_id);
}
