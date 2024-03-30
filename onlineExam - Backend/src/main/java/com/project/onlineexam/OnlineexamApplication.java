package com.project.onlineexam;

import com.project.onlineexam.DTO.QuestionExamDTO;
import com.project.onlineexam.Entity.*;
import com.project.onlineexam.Entity.Enums.*;
import com.project.onlineexam.Service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@SpringBootApplication
public class OnlineexamApplication {

	public static void main(String[] args) throws UnknownHostException {

		ApplicationContext ac =  SpringApplication.run(OnlineexamApplication.class, args);
		System.out.println("Application started...well");

		LocalDateTime start_time = LocalDateTime.of(2024,02,06,9,00);
		LocalDateTime end_time = LocalDateTime.of(2024,02,8,22,00);

//		Exam exam = new Exam(1,20,2,20,2,start_time,end_time);
//		ExamService es = ac.getBean(ExamService.class);
//		es.saveExam(exam);
//		Exam e = es.getExamById(2);
//
//		QuestionService qs = ac.getBean(QuestionService.class);
//		Questions q = qs.getQuestionById(6);

		UserService us = ac.getBean(UserService.class);
//		System.out.println(us.findByEmail("darshan@gmail.com"));
//		System.out.println(us.findByEmail("darshn@gmail.com"));
//		System.out.println(us.getAllUsersByRole(Role.ADMIN));
//		Questions q = new Questions(1,QuestionType.PROGRAMMING,"quest","aa","bb","cc","dd",Answer.OPTION_A,Difficulty_Level.MEDIUM,Categories.LOGICAL);
		QuestionService qs = ac.getBean(QuestionService.class);



//		qs.saveQuestion(q);
//		qs.getRandomQuestions(QuestionType.MCQ,Difficulty_Level.MEDIUM);
		Random rand = new Random();



//		QuestionExam qu = new QuestionExam(1,e,q);
//		QuestionExamService qsx = ac.getBean(QuestionExamService.class);
//		qsx.saveQuestionExam(qu);

//		StudentExamService ses = ac.getBean(StudentExamService.class);
//
//		System.out.println(ses.getStudentExamDetailByStudentId(1));

//		QuestionExamService qs = ac.getBean(QuestionExamService.class);
//		System.out.println(qs.getAllQuestionsByExamId(10) );

//		System.out.println(InetAddress.getLocalHost());




	}

}
