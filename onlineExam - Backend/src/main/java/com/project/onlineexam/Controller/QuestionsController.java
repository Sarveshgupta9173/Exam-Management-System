package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.QuestionsDTO;
import com.project.onlineexam.Entity.Enums.Difficulty_Level;
import com.project.onlineexam.Entity.Enums.QuestionType;
import com.project.onlineexam.Entity.Questions;
import com.project.onlineexam.Service.QuestionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class QuestionsController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get")
    public ResponseEntity<List<QuestionsDTO>> getAllQuestions() {
        List<Questions> questions = null;
        try {
            questions = questionService.getAllQuestions();
            if (questions.isEmpty()) {
                throw new ClassNotFoundException("Error ...No Questions found.");
            }
            //converting object to dto and then returning it
            List<QuestionsDTO> questiondto = questions.stream().map(question -> modelMapper
                    .map(question, QuestionsDTO.class)).collect(Collectors.toList());

            return new ResponseEntity<>(questiondto, HttpStatus.OK);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<QuestionsDTO> getQuestionsById(@PathVariable int id){
        try{
            Questions question = questionService.getQuestionById(id);
            if(question == null){
                throw new ClassNotFoundException("No class found with id "+id);
            }else{
                QuestionsDTO questiondto = modelMapper.map(question, QuestionsDTO.class);
                return new ResponseEntity<>(questiondto,HttpStatus.OK);
            }
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            return new  ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        }


    }

    @PostMapping("/create-question")
    public ResponseEntity<QuestionsDTO> createQuestion(@RequestBody QuestionsDTO questiondto){
        try{
            // converting dto to object
            Questions question = modelMapper.map(questiondto, Questions.class);
            questionService.saveQuestion(question);

            //converting object to dto
            QuestionsDTO questionDto = modelMapper.map(question, QuestionsDTO.class);
            return new ResponseEntity<>(questionDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<QuestionsDTO> updateQuestion(@PathVariable int id, @RequestBody QuestionsDTO questiondto){
        try{
            // converting dto to object
            Questions question  = modelMapper.map(questiondto, Questions.class);
            Questions newQuestion = questionService.updateQuestion(question,id);
            if(newQuestion == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            //converting object to dto
            QuestionsDTO questionDto = modelMapper.map(newQuestion, QuestionsDTO.class);
            return new ResponseEntity<>(questionDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(questiondto,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Questions> deleteQuestionByID(@PathVariable int id){
        try{
            Questions questions = questionService.deleteQuestionById(id);
            return new ResponseEntity<>(questions,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-mcq-questions-on-type-and-level/{limit}/{exam_level}")
    public ResponseEntity<List<QuestionsDTO>> getMcqQuestionsOnTypeAndLevel(@PathVariable int limit,@PathVariable String exam_level){
        try{
            List<Questions> questions =  questionService.getMcqQuestionsOnTypeAndLevel(limit,exam_level);
            if(questions == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            List<QuestionsDTO> questionsDTOS = questions.stream().map(element->modelMapper.map(element,QuestionsDTO.class))
                    .collect(Collectors.toList());

            return  new ResponseEntity<>(questionsDTOS,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-program-questions-on-type-and-level/{limit}/{exam_level}")
    public ResponseEntity<List<QuestionsDTO>> getProgramQuestionsOnTypeAndLevel(@PathVariable int limit,@PathVariable String exam_level){
        try{
            List<Questions> questions = questionService.getProgramQuestionsOnTypeAndLevel(limit, exam_level);
            if(questions == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            List<QuestionsDTO> questionsDTOS = questions.stream().map(element->modelMapper.map(element,QuestionsDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(questionsDTOS,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-question-count")
    public ResponseEntity<HashMap<String,Integer>> getQuestioncount(){
        try{
            HashMap<String,Integer> map = questionService.getQuestionCount();
            if(map.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }else{
                return  new ResponseEntity<>(map,HttpStatus.OK);
            }
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

}
