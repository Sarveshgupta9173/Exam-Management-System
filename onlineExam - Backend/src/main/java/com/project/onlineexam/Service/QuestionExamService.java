package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.QuestionExam;
import com.project.onlineexam.Entity.StudentExam;
import com.project.onlineexam.Repository.QuestionExamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionExamService {

    @Autowired
    private QuestionExamRepository questionExamRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<QuestionExam> getAllQuestionExam(){
        return questionExamRepository.findAll();
    }

    public QuestionExam getQuestionExamById(int id){
        return questionExamRepository.findById(id).get();
    }

    public void deleteQuestionExam(int id){
        questionExamRepository.deleteById(id);
        System.out.println("Deleted Successfully..");
    }

    public QuestionExam updateStudentExam(QuestionExam questionExam,int id){
        QuestionExam old = questionExamRepository.findById(id).get();
        old.setQuestion(questionExam.getQuestion());
        old.setExam(questionExam.getExam());
        old.setId(questionExam.getId());
        questionExamRepository.save(old);
        return old;
    }

    public QuestionExam saveQuestionExam(QuestionExam questionExam){
        QuestionExam questionexam = questionExamRepository.save(questionExam);
        return questionexam;
    }

    public Boolean saveAllQuestionExam(List<QuestionExam> questionExams){
        List<QuestionExam> questionExams1 = questionExamRepository.saveAll(questionExams);
        if (questionExams1.isEmpty()){
            return false;
        }
        return true;
    }

    public List<QuestionExam> getAllQuestionsByExamId(int exam_id){
        List<QuestionExam> questionExam = questionExamRepository.getAllQuestionsByExamId(exam_id);
        if(questionExam == null){
            return null;
        }else{
            return questionExam;
        }
    }
}
