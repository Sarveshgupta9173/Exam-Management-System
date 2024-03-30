package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.QuestionType;
import com.project.onlineexam.Entity.Questions;
import com.project.onlineexam.Repository.QuestionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.awt.print.Pageable;
import java.util.*;

@Service
public class QuestionService {
    @Autowired
    private QuestionsRepository questionsRepository;


    public Questions saveQuestion(Questions question){
        if(question == null){
            return null;
        }
        questionsRepository.save(question);
        return question;
    }

    public Questions getQuestionById(int id){
        Questions question = questionsRepository.findById(id).orElse(null);
        if(question == null){
            return null;
        }else {
            return question;
        }
    }

    public List<Questions> getAllQuestions(){
        List<Questions> questions = questionsRepository.findAll();
        if(questions.isEmpty()){
            return null;
        }
        return questions;
    }

    public Questions deleteQuestionById(int id){
        Questions questions = questionsRepository.findById(id).orElse(null);
        if(questions == null){
            return null;

        }else {
            questionsRepository.delete(questions);
            return questions;
        }

    }

    public Questions deleteQuestion(Questions question){
        if(question == null){
            return null;
        }
        questionsRepository.delete(question);
        return question;
    }

    public List<Questions> getMcqQuestionsOnTypeAndLevel(int limit,String exam_level) {
        QuestionType type = QuestionType.MCQ;
        List<Questions> allQuestions = new ArrayList<>();

        List<Questions> easyQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type,  Difficulty_Level.EASY);
        Collections.shuffle(easyQuestions);
        List<Questions> mediumQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type, Difficulty_Level.MEDIUM);
        Collections.shuffle(mediumQuestions);
        List<Questions> hardQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type, Difficulty_Level.HARD);
        Collections.shuffle(hardQuestions);

        if (exam_level.equalsIgnoreCase("easy")) {
            // ratio of questions e:m:h  5:3:2
            int easy = (int) Math.floor(limit * 0.5);
            int medium = (int) Math.ceil(limit * 0.3);
            int hard = (int) Math.floor(limit * 0.2);


            for (int i = 0; i < easy; i++) {
                Questions question = easyQuestions.get(i);
                allQuestions.add(question);

            }

            for (int j = 0; j < medium; j++) {
                Questions question = mediumQuestions.get(j);
                allQuestions.add(question);
            }

            for (int k = 0; k < hard; k++) {
                Questions question = hardQuestions.get(k);
                allQuestions.add(question);
            }

        } else if (exam_level.equalsIgnoreCase("hard")) {
            //ratio of questions 2:3:5
            int easy = (int) Math.floor(limit * 0.2);
            int medium = (int) Math.ceil(limit * 0.3);
            int hard = (int) Math.floor(limit * 0.5);

            for (int i = 0; i < easy; i++) {
                Questions question = easyQuestions.get(i);
                allQuestions.add(question);
            }

            for (int j = 0; j < medium; j++) {
                Questions question = mediumQuestions.get(j);
                allQuestions.add(question);
            }

            for (int k = 0; k < hard; k++) {
                Questions question = hardQuestions.get(k);
                allQuestions.add(question);
            }


        } else {// medium level by default

            // ration of questions 3:5:2
            int easy = (int) Math.floor(limit * 0.3);
            int medium = (int) Math.ceil(limit * 0.5);
            int hard = (int) Math.floor(limit * 0.2);

            for (int i = 0; i < easy; i++) {
                Questions question = easyQuestions.get(i);
                allQuestions.add(question);
            }

            for (int j = 0; j < medium; j++) {
                Questions question = mediumQuestions.get(j);
                allQuestions.add(question);
            }

            for (int k = 0; k < hard; k++) {
                Questions question = hardQuestions.get(k);
                allQuestions.add(question);
            }
        }
        if(allQuestions.size()<limit){
            Questions questions = mediumQuestions.get(mediumQuestions.size()-1);
            allQuestions.add(questions);
        }
        return allQuestions;
    }

    public List<Questions> getProgramQuestionsOnTypeAndLevel(int limit,String exam_level){
        QuestionType type1 = QuestionType.PROGRAMMING;
        List<Questions> allQuestions = new ArrayList<>();

        if(exam_level.equalsIgnoreCase("easy")){
            List<Questions> easyQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type1,Difficulty_Level.EASY);
            Collections.shuffle(easyQuestions);
            for(int i=0;i<limit;i++){
                Questions question = easyQuestions.get(i);
                allQuestions.add(question);
            }

        } else if (exam_level.equalsIgnoreCase("hard")) {
            List<Questions> hardQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type1,Difficulty_Level.HARD);
            Collections.shuffle(hardQuestions);
            for(int i=0;i<limit;i++){
                Questions question = hardQuestions.get(i);
            }
        }else{
            List<Questions> mediumQuestions = questionsRepository.getQuestionsOnTypeAndLevel(type1,Difficulty_Level.MEDIUM);
            Collections.shuffle(mediumQuestions);
            for(int i=0;i<limit;i++){
                Questions question = mediumQuestions.get(i);
                allQuestions.add(question);
            }
        }
        return  allQuestions;
    }

    public HashMap<String ,Integer> getQuestionCount(){
        HashMap<String,Integer> map = new HashMap<>();
        int mcq_count = questionsRepository.getQuestionCount(QuestionType.MCQ);
        int program_count = questionsRepository.getQuestionCount(QuestionType.PROGRAMMING);
        map.put("mcq_count",mcq_count);
        map.put("program_count",program_count);
        return map;
    }


    public Questions updateQuestion(Questions newQuestion,int id){
        Questions oldQuestion = questionsRepository.findById(id).orElse(null);
        if(oldQuestion == null){
            return null;
        }
        oldQuestion.setQuestion_id(newQuestion.getQuestion_id());
        oldQuestion.setQuestion(newQuestion.getQuestion());
        oldQuestion.setOption_A(newQuestion.getOption_A());
        oldQuestion.setOption_B(newQuestion.getOption_B());
        oldQuestion.setOption_C(newQuestion.getOption_C());
        oldQuestion.setOption_D(newQuestion.getOption_D());
        oldQuestion.setAnswer(newQuestion.getAnswer());
        oldQuestion.setDifficulty_level(newQuestion.getDifficulty_level());
        oldQuestion.setCategories(newQuestion.getCategories());

        Questions questions = questionsRepository.save(oldQuestion);
        return questions;
    }


}
