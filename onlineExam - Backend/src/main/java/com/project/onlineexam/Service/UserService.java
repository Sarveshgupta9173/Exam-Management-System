package com.project.onlineexam.Service;

import com.project.onlineexam.Entity.Enums.Role;
import com.project.onlineexam.Entity.User;
import com.project.onlineexam.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        if(user == null){
            return null;
        }
        return userRepository.save(user);
    }

    public User updateUser(User newUser,int user_id){
        User oldUser = userRepository.findById(user_id).orElse(null);
        if(oldUser == null){
            return null;
        }

        oldUser.setUser_id(newUser.getUser_id());
        oldUser.setFirst_name(newUser.getFirst_name());
        oldUser.setLast_name(newUser.getLast_name());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setPassword(newUser.getPassword());
        oldUser.setRole(newUser.getRole());

        userRepository.save(oldUser);
        return oldUser;
    }

    public List<User> getAllUsers(){
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return  null;
        }
        return users;
    }

    public User getUserById(int id){
            User user = userRepository.findById(id).orElse(null);
            if(user == null){
                return null;
            }
                return user;

    }

    public void deleteUser(int id){
        userRepository.deleteById(id);
    }

    public boolean isAdmin(int id){
        if (userRepository.existsById(id)) {
            User user = userRepository.findById(id).orElse(null);
            if(user.getRole().toString().toUpperCase() == "ADMIN") {
                return true;
            } else{
                return false;
            }

        }else{
            return false;
        }
    }

    public User findByEmail( String email){
       User user1 =  userRepository.findByEmail(email);
       if(user1 == null){
           return null;
       }else {
           return user1;
       }
    };

    public List<User> getAllUsersByRole(Role role){
            List<User> users = userRepository.getAllUsersByRole(role);
            if(users == null){
                return null;
            }else {
                return users;
            }
    }

    public List<User> getUserByEmail(String email){
        List<User> user = userRepository.getUserByEmail(email);
        if(user == null){
            return null;
        }else{
            return user;
        }
    }

}

