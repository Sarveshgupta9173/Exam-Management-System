package com.project.onlineexam.Controller;


import com.project.onlineexam.DTO.UserExamSubmissionDTO;
import com.project.onlineexam.Entity.UserExamSubmission;
import com.project.onlineexam.Service.UserExamSubmissionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-exam-submission")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserExamSubmissionController {

    @Autowired
    private UserExamSubmissionService userExamSubmissionService;
    @Autowired
    private ModelMapper modelMapper;

    // useful for insertion/updation selected answers
    @PostMapping("/insert-or-update")
    public ResponseEntity<UserExamSubmissionDTO> insertOrUpdateUserExamSubmission(@RequestBody  UserExamSubmissionDTO userExamSubmissionDTO){
        try{
            if(userExamSubmissionDTO == null){
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            UserExamSubmission userExamSubmission = modelMapper.map(userExamSubmissionDTO,UserExamSubmission.class);
            UserExamSubmission userExamSubmission1 =  userExamSubmissionService.insertOrUpdateUserExamSubmission(userExamSubmission);

            UserExamSubmissionDTO userExamSubmissionDTO1 = modelMapper.map(userExamSubmission1,UserExamSubmissionDTO.class);
            return new ResponseEntity<>(userExamSubmissionDTO1,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-student-id-exam-id/{student_id}/{exam_id}")
    public ResponseEntity<List<UserExamSubmissionDTO>>  getByStudentIdExamId(
            @PathVariable("student_id") int student_id,@PathVariable("exam_id") int exam_id){
        try{
            List<UserExamSubmission> listObj = userExamSubmissionService.getByStudentIdExamId(student_id, exam_id);
            if(listObj.isEmpty()){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }else{
                List<UserExamSubmissionDTO> userExamSubmissionDTOS = listObj.stream().map(element->modelMapper.map(element,UserExamSubmissionDTO.class)).collect(Collectors.toList());
                return new ResponseEntity<>(userExamSubmissionDTOS,HttpStatus.OK);
            }
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-result/{student_id}/{exam_id}")
    public ResponseEntity<Integer> getResult(@PathVariable("student_id") int student_id,@PathVariable("exam_id") int exam_id){
        try{
            if(userExamSubmissionService.getResult(student_id,exam_id) == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            int marks = userExamSubmissionService.getResult(student_id, exam_id);
            return  new ResponseEntity<>(marks,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }





}
