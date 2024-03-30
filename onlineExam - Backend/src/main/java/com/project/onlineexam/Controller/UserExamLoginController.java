package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.UserExamLoginDTO;
import com.project.onlineexam.Entity.UserExamLogin;
import com.project.onlineexam.Service.UserExamLoginService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-exam-login")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserExamLoginController {

    @Autowired
    private UserExamLoginService userExamLoginService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/insert-or-update-user-exam-login-details")
    public ResponseEntity<UserExamLoginDTO> createUserExamLogin(@RequestBody UserExamLoginDTO userExamLoginDTO) throws UnknownHostException {
        try{
            if(userExamLoginDTO == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

            UserExamLogin userExamLogin = modelMapper.map(userExamLoginDTO,UserExamLogin.class);
            UserExamLogin   userExamLogin1 = userExamLoginService.insertOrUpdateUserExamLogin(userExamLogin);

            UserExamLoginDTO userExamLoginDTO1 = modelMapper.map(userExamLogin1,UserExamLoginDTO.class);
            return new ResponseEntity<>(userExamLoginDTO1, HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-user-exam-login-details/{student_id}/{exam_id}")
    public ResponseEntity<UserExamLoginDTO> getUserExamloginByStudentIdExamId(@PathVariable int student_id,@PathVariable int exam_id){
        try{
            UserExamLogin userExamLogins = userExamLoginService.getByStudentIdExamId(student_id, exam_id);
            if(userExamLogins == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            UserExamLoginDTO userExamLoginDTOS = modelMapper.map(userExamLogins,UserExamLoginDTO.class);
            return new ResponseEntity<>(userExamLoginDTOS,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }


}
