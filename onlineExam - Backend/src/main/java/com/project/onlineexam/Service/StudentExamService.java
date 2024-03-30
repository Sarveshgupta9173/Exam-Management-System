package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.Enums.Status;
import com.project.onlineexam.Entity.StudentExam;
import com.project.onlineexam.Repository.StudentExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentExamService {
    @Autowired
    private StudentExamRepository studentExamRepository;

    @Autowired
    private UserExamSubmissionService userExamSubmissionService;

    public StudentExam saveStudentExam(StudentExam studentExam){

        StudentExam studentExam1 = studentExamRepository.save(studentExam);
        if(studentExam1 == null){
            return null;
        }
        return studentExam1;
    }

    public void deletestudentExamById(int id){

        studentExamRepository.deleteById(id);
    }

    public List<StudentExam> getAllStudentExamDetails(){
        return studentExamRepository.findAll();
    }

    public List<StudentExam> getAllUsersByStatus(Status status){
        return studentExamRepository.getAllUsersByStatus(status);
    }

    public StudentExam getStudentExamById(int id){
        return studentExamRepository.findById(id).get();
    }

    public List<StudentExam> getStudentExamDetailByStudentId(int student_id){
        List<StudentExam>  studentexam = studentExamRepository.getStudentExamDetailsByStudentId(student_id);
        if(studentexam == null){
            return null;
        }else{
            return studentexam;
        }
    }

    public StudentExam getByStudentIdExamId(int student_id,int exam_id){
        StudentExam studentExam = studentExamRepository.getByStudentIdExamId(student_id, exam_id);

        if(studentExam == null){
            return null;
        }else{
            return studentExam;
        }
    }

    public StudentExam updateStudentExam(StudentExam studentExam){
        if(studentExam == null){
            return null;
        }
        int student_id = studentExam.getStudent().getUser_id();
        int exam_id = studentExam.getExam().getExam_id();
        StudentExam oldObj = studentExamRepository.getByStudentIdExamId(student_id,exam_id);

        if(studentExam.getStatus().toString().equalsIgnoreCase( "COMPLETED")){ // update total marks only when test is completed
            oldObj.setStatus(studentExam.getStatus());
            int marks_scored = userExamSubmissionService.getResult(student_id,exam_id);
            System.out.println(marks_scored);
            oldObj.setTotal_marks_scored(marks_scored);

            System.out.println(oldObj.toString()+" completed");
            return studentExamRepository.save(oldObj);
        }else{// insert old object without any changes
            // update status only
            if(studentExam.getStatus().toString().isEmpty()){
             return   studentExamRepository.save(studentExam);
            }
            oldObj.setStatus(studentExam.getStatus());

            System.out.println(oldObj.toString()+" without changes");

            return studentExamRepository.save(oldObj);
        }


    }
}
