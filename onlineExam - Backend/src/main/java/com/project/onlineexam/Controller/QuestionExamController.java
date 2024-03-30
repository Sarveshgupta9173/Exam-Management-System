package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.QuestionExamDTO;
import com.project.onlineexam.DTO.StudentExamDTO;
import com.project.onlineexam.Entity.QuestionExam;
import com.project.onlineexam.Entity.StudentExam;
import com.project.onlineexam.Service.QuestionExamService;
import com.project.onlineexam.Service.QuestionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question-exam")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class QuestionExamController {

    @Autowired
    private QuestionExamService questionExamService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get-all")
    public ResponseEntity<List<QuestionExamDTO>> getAllQuestionExam(){
        try{
            List<QuestionExam> questionExams = questionExamService.getAllQuestionExam();
            if(questionExams.isEmpty()){
                throw new ClassNotFoundException("No Questions found");
            }
            List<QuestionExamDTO> questionExamDTOS = questionExams.stream().map(item->modelMapper.map(item,QuestionExamDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(questionExamDTOS, HttpStatus.OK);
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/insert-question-exam")
    public ResponseEntity<QuestionExamDTO> saveQuestionExam(@RequestBody QuestionExamDTO questionexamdto){
        try{
            QuestionExam questionexam = modelMapper.map(questionexamdto,QuestionExam.class);

            questionExamService.saveQuestionExam(questionexam);
            QuestionExamDTO questionexamDto = modelMapper.map(questionexam,QuestionExamDTO.class);
            return new ResponseEntity<>(questionexamDto, HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-all-questions-by-exam-id/{exam_id}")
    public ResponseEntity<List<QuestionExamDTO>> getAllQuestionsByExamId(@PathVariable int exam_id){
        try{
            List<QuestionExam> questionExams = questionExamService.getAllQuestionsByExamId(exam_id);

            if(questionExams == null){
                return new ResponseEntity<>(null,HttpStatus.OK);
            }else{
                List<QuestionExamDTO> questionExamDTOS = questionExams.stream().map(element->modelMapper
                        .map(element,QuestionExamDTO.class)).collect(Collectors.toList());
                return  new ResponseEntity<>(questionExamDTOS,HttpStatus.OK);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.OK);
        }

    }

    @PostMapping("/save-all")
    public ResponseEntity<List<QuestionExamDTO>> saveAllQuestionExams(@RequestBody List<QuestionExamDTO> questionExamDTOS){
        try{
            if(questionExamDTOS.isEmpty()){
                return new ResponseEntity<>(questionExamDTOS,HttpStatus.BAD_REQUEST);
            }
            List<QuestionExam> questionExams = questionExamDTOS.stream().map(element->modelMapper.map(element,QuestionExam.class)).collect(Collectors.toList());
            questionExamService.saveAllQuestionExam(questionExams);

            List<QuestionExamDTO> questionExamDTOS1 = questionExams.stream().map(element->modelMapper.map(element,QuestionExamDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(questionExamDTOS1,HttpStatus.OK);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(questionExamDTOS,HttpStatus.BAD_REQUEST);
        }

    }
}
