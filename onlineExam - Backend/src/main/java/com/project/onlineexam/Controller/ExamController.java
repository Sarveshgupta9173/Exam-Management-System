package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.ExamDTO;
import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Service.ExamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/exam")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ExamController {

    @Autowired
    private ExamService examService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/get")
    public ResponseEntity<List<ExamDTO>> getAllExams(){
        try{
            List<Exam> exams = examService.getAllExams();
            if(exams.isEmpty()){
                throw new ClassNotFoundException("Error :No classes found");
            }
            //converting object to dto and then returning it
            List<ExamDTO> listdto =  exams.stream().map(exam->modelMapper.map(exam, ExamDTO.class))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(listdto,HttpStatus.OK);
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable int id){
        try{
            Exam exam = examService.getExamById(id);
            if(exam == null){
                throw new ClassNotFoundException("Class not found with id "+ id);
            }
            ExamDTO examdto = modelMapper.map(exam, ExamDTO.class);
            return new ResponseEntity<>(examdto,HttpStatus.OK);
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/create-exam")
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examdto){
        try{
            // converting dto to object
            Exam exam = modelMapper.map(examdto,Exam.class);
            Exam exam1 = examService.saveExam(exam);

            //converting object to dto
            ExamDTO examDto = modelMapper.map(exam1, ExamDTO.class);
            return new ResponseEntity<>(examDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(examdto,HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ExamDTO> updateExam(@PathVariable int id, @RequestBody ExamDTO examdto){
        try{
            // converting dto to object
            Exam exam  = modelMapper.map(examdto, Exam.class);
            Exam newExam = examService.updateExam(exam,id);

            //converting object to dto
            ExamDTO examDto = modelMapper.map(exam, ExamDTO.class);
            return new ResponseEntity<>(examDto,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(examdto,HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteExamByID(@PathVariable int id){
        try{
            examService.deleteExamById(id);
            return new ResponseEntity<>(null,HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }


}
