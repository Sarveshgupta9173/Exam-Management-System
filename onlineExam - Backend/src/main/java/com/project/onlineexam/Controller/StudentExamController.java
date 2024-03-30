package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.StudentExamDTO;
import com.project.onlineexam.Entity.Enums.Status;
import com.project.onlineexam.Entity.Exam;
import com.project.onlineexam.Entity.StudentExam;
import com.project.onlineexam.Service.StudentExamService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student-exam")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class StudentExamController {

    @Autowired
    private StudentExamService studentExamService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/insert-student-exam")
    public ResponseEntity<StudentExamDTO> saveStudentExam(@RequestBody StudentExamDTO studentexamdto){
        try{
            StudentExam studentexam = modelMapper.map(studentexamdto,StudentExam.class);
            System.out.println(studentexam);
            studentExamService.saveStudentExam(studentexam);

            StudentExamDTO studentexamDto = modelMapper.map(studentexam,StudentExamDTO.class);
            return new ResponseEntity<>(studentexamDto, HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-all")
    public ResponseEntity<List<StudentExamDTO>> getAllStudentExamDetails(){
        try{
            List<StudentExam> studentExams = studentExamService.getAllStudentExamDetails();
            List<StudentExamDTO> studentExamDTOS = studentExams.stream().map(item -> modelMapper.map(item,StudentExamDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(studentExamDTOS,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<StudentExamDTO> getAllStudentExamDetailsById(@PathVariable int id){
//        StudentExam studentExams = studentExamService.getStudentExamById(id);
//        StudentExamDTO studentExamDTOS = modelMapper.map(studentExams,StudentExamDTO.class);
//        return new ResponseEntity<>(studentExamDTOS,HttpStatus.OK);
//    }

    @GetMapping("/get-all-by-status/{status}")
    public ResponseEntity<List<StudentExamDTO>> getAllStudentsByStatus(@PathVariable Status status){
        try{
            List<StudentExam> list = studentExamService.getAllUsersByStatus(status);

            List<StudentExamDTO> Dtolist = list.stream().map(item->modelMapper.map(item,StudentExamDTO.class)).collect(Collectors.toList());

            return new ResponseEntity<>(Dtolist,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/get-by-student-id/{student_id}")
    public ResponseEntity<List<StudentExamDTO>> getStudentExamDetailByStudentId(@PathVariable int student_id){
        try{
            List<StudentExam> studentExam = studentExamService.getStudentExamDetailByStudentId(student_id);

            if(studentExam != null){
                List<StudentExamDTO> studentExamDTO = studentExam.stream()
                        .map(element->modelMapper.map(element,StudentExamDTO.class)).collect(Collectors.toList());
                return new ResponseEntity<>(studentExamDTO,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(null,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-by-student-id-exam-id/{student_id}/{exam_id}")
    public ResponseEntity<StudentExamDTO> getStudentExamDetailByStudentIdExamId(@PathVariable int student_id,@PathVariable int exam_id){
        try{
            StudentExam studentExam = studentExamService.getByStudentIdExamId(student_id,exam_id);
            if(studentExam == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            StudentExamDTO studentExamDTO = modelMapper.map(studentExam,StudentExamDTO.class);
            return new ResponseEntity<>(studentExamDTO,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-student-exam-details")
    public ResponseEntity<StudentExamDTO> updateStudentExamDetails(@RequestBody StudentExamDTO studentExamDTO){
        try{
            if(studentExamDTO == null){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
            StudentExam studentExam = modelMapper.map(studentExamDTO,StudentExam.class);
            StudentExam studentExam1 = studentExamService.updateStudentExam(studentExam);

            StudentExamDTO studentExamDTO1 = modelMapper.map(studentExam1,StudentExamDTO.class);
            return new ResponseEntity<>(studentExamDTO1,HttpStatus.OK);
        }catch (Exception e){
            return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }




}
