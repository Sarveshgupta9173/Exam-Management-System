package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.User;
import com.project.onlineexam.Entity.UserExamSubmission;
import com.project.onlineexam.Repository.UserExamSubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserExamSubmissionService {

    @Autowired
    private UserExamSubmissionRepository userExamSubmissionRepository;

    // useful for both insertion and updation of user questions
    public UserExamSubmission insertOrUpdateUserExamSubmission(UserExamSubmission userExamSubmission){
        if(userExamSubmission == null){
            return null;
        }
        int student_id = userExamSubmission.getStudent().getUser_id();
        int exam_id = userExamSubmission.getExam().getExam_id();
        int question_id = userExamSubmission.getQuestion().getQuestion_id();

        // checking if given mcq answer is correct or wrong
        // enters the marks for mcq question
        if(userExamSubmission.getQuestion().getQuestion_type().toString().equalsIgnoreCase("MCQ")){
            if(userExamSubmission.getAnswer().toString().equalsIgnoreCase(userExamSubmission.getQuestion().getAnswer().toString())){
                userExamSubmission.setMarks_obtained(1);
            }else{
                userExamSubmission.setMarks_obtained(0);
            }
        }

        UserExamSubmission oldObj = userExamSubmissionRepository.getByStudentIdExamIdQuestionId(student_id,exam_id,question_id);

        // handles new insertion

        if(oldObj == null){
            return userExamSubmissionRepository.save(userExamSubmission);
        }else{// handles updation part

            // handles question updation during marks updation
            if(userExamSubmission.getAnswer().isEmpty()){
                oldObj.setAnswer(oldObj.getAnswer());
            }else{
                oldObj.setAnswer(userExamSubmission.getAnswer());
            }
            oldObj.setMarks_obtained(userExamSubmission.getMarks_obtained());
            return userExamSubmissionRepository.save(oldObj);
        }
    }


    // useful for fetching result purpose
    public List<UserExamSubmission> getByStudentIdExamId(int student_id, int exam_id){
        List<UserExamSubmission> objList = userExamSubmissionRepository.getByStudentIdExamId(student_id, exam_id);
        if(objList == null){
            return null;
        }else{
            return objList;
        }
    }

    public Integer getResult(int student_id,int exam_id){
        List<UserExamSubmission> userExamSubmissions = userExamSubmissionRepository.getByStudentIdExamId(student_id, exam_id);
        if(userExamSubmissions.isEmpty()){
            return null;
        }
        int final_marks = 0;
        for(int i=0;i< userExamSubmissions.size();i++){
            int marks = userExamSubmissions.get(i).getMarks_obtained();
            final_marks+=marks;
        }
        return  final_marks;
    }


}
