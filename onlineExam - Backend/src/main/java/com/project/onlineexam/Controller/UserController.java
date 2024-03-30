
package com.project.onlineexam.Controller;

import com.project.onlineexam.DTO.*;
import com.project.onlineexam.Entity.*;
import com.project.onlineexam.Entity.Enums.Role;
import com.project.onlineexam.Service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

    @RestController
    @RequestMapping("/user")
    @CrossOrigin(origins = "*",allowedHeaders = "*")
    public class UserController {
        @Autowired
        private UserService userService;
        @Autowired
        private QuestionService questionService;
        @Autowired
        private ExamService examService;
        @Autowired
        private ModelMapper modelMapper;

        // --------------------------ADMIN FUNCTIONALITIES START------------------------------------//

        @GetMapping("/get-all")
        public ResponseEntity<List<UserDTO>> getAllUsers() {
            try{
                List<User> users = userService.getAllUsers();
                //converting object to dto and then returning it
                List<UserDTO> userDTOS = users.stream().map(admin -> modelMapper.map(admin, UserDTO.class))
                        .collect(Collectors.toList());
                return  new ResponseEntity<>(userDTOS,HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }

        @GetMapping("/get/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable int id) {
            try{
                User user = userService.getUserById(id);
                UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }

        @PostMapping("/create-user")
        public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userdto) {
            try{
                User user = modelMapper.map(userdto, User.class);
                userService.saveUser(user);

                UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                return new ResponseEntity<>(userDTO, HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }

        @PutMapping("/update/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody UserDTO userdto) {
            try{
                User user = modelMapper.map(userdto, User.class);
                User newUser = userService.updateUser(user,id);

                //converting object to dto
                UserDTO userDTO = modelMapper.map(newUser, UserDTO.class);
                return new ResponseEntity<>(userDTO,HttpStatus.OK);
            }catch (Exception e){
                return  new ResponseEntity<>(userdto,HttpStatus.BAD_REQUEST);
            }

        }

        @DeleteMapping("/delete/{id}")
        public ResponseEntity<HttpStatus> deleteUserByID(@PathVariable int id) {
            try{
                userService.deleteUser(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }catch (Exception e){
                return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

        @GetMapping("/is-admin/{email}")
        public ResponseEntity<UserDTO> isAdmin(@PathVariable String email) {
            try{
                User user = userService.findByEmail(email);
                if(user == null){
                    return  new ResponseEntity<>(null,HttpStatus.OK);
                } else {
                    UserDTO userDTO = modelMapper.map(user, UserDTO.class);
                    if(user.getEmail().trim().equals(email)){
                        return new ResponseEntity<>(userDTO,HttpStatus.OK);

                    }else{
                        return  new ResponseEntity<>(userDTO,HttpStatus.BAD_REQUEST);
                    }
                }
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }
        @GetMapping("/get-by-role/{role}")
        public ResponseEntity<List<UserDTO>> getUserByRole(@PathVariable Role role){
            try{
                if(role.toString().equals("ADMIN") || role.toString().equals(("STUDENT"))){

                    List<User> users = userService.getAllUsersByRole(role);

                    List<UserDTO> userDTOList = users.stream().map(user->modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
                    return new ResponseEntity<>(userDTOList,HttpStatus.OK);
                }else{
                    throw new NoSuchElementException("Error.....Enter Valid Role");
                }
            }catch (Exception e){
                return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }

        @GetMapping("/get-by-email/{email}")
        public ResponseEntity<List<UserDTO>> getUserByEmail(@PathVariable String email){
            try{
                List<User> user = userService.getUserByEmail(email);
                if(user == null){
                    return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
                }
                List<UserDTO> userDTOS = user.stream().map(element -> modelMapper.map(element, UserDTO.class))
                        .collect(Collectors.toList());

                return new ResponseEntity<>(userDTOS,HttpStatus.OK);
            }catch (Exception e){
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }

        }

    }
