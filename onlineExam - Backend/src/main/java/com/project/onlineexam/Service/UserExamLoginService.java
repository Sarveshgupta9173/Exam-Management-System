package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.UserExamLogin;
import com.project.onlineexam.Entity.UserExamSubmission;
import com.project.onlineexam.Repository.UserExamLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Service
public class UserExamLoginService {

    @Autowired
    private UserExamLoginRepository userExamLoginRepository;

    public UserExamLogin insertOrUpdateUserExamLogin(UserExamLogin userExamLogin) throws UnknownHostException {
        if(userExamLogin == null){
            return null;
        }
        int student_id = userExamLogin.getStudent().getUser_id();
        int exam_id = userExamLogin.getExam().getExam_id();

        UserExamLogin oldUserExamLogin =  userExamLoginRepository.getByStudentIdExamId(student_id,exam_id);

        if(oldUserExamLogin == null){ // insertion case
            userExamLogin.setIp_address(String.valueOf(InetAddress.getLocalHost()));
            return userExamLoginRepository.save(userExamLogin);
        }else{ // updation case
            oldUserExamLogin.setRemaining_time(userExamLogin.getRemaining_time());
            oldUserExamLogin.setEnd_time(userExamLogin.getEnd_time());
            return userExamLoginRepository.save(oldUserExamLogin);
        }
    }

    // useful for resuming and restarting the exam purpose
    public UserExamLogin getByStudentIdExamId(int student_id, int exam_id){
        UserExamLogin obj = userExamLoginRepository.getByStudentIdExamId(student_id, exam_id);
        if(obj == null){
            return null;
        }else{
            return obj;
        }
    }
}
