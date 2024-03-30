package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public Exam saveExam(Exam exam){
        int duration = (int) exam.getDuration()*60; // converting to minutes
       Exam exam1 =  examRepository.save(exam);
       if(exam1 == null){
           return null;
       }
       return  exam1;

    }

    public Exam getExamById(int id){
        Exam exam = examRepository.findById(id).orElse(null);
        if(exam == null){
            return null;
        }else {
            return exam;
        }
    }

    public List<Exam> getAllExams(){
        List<Exam> exams = examRepository.findAll();
        if(exams.isEmpty()){
            return null;
        }
        return exams;
    }

    public void deleteExamById(int id){
        Exam exam = examRepository.findById(id).orElse(null);
        if(exam == null){
            return ;
        }else {
            examRepository.delete(exam);
        }

    }

    public void deleteExam(Exam exam){
            if(exam == null){
                return ;
            }
            examRepository.delete(exam);
    }

    public Exam updateExam(Exam newExam,int id){
        Exam oldExam = examRepository.findById(id).orElse(null);
        if(oldExam == null){
            System.out.println("Please Enter a Valid Exam Id");
            return null;
        }
//        oldExam.setExam_id(newExam.getExam_id());
        oldExam.setNo_of_mcq_questions(newExam.getNo_of_mcq_questions());
        oldExam.setPass_marks(newExam.getPass_marks());
        oldExam.setDuration(newExam.getDuration());
        oldExam.setNo_of_programming_questions(newExam.getNo_of_programming_questions());
        oldExam.setStart_time(newExam.getStart_time());
        oldExam.setEnd_time(newExam.getEnd_time());

        examRepository.save(oldExam);

        return examRepository.findById(id).orElse(null);
    }

}
