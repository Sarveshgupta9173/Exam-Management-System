package com.project.onlineexam.Repository;

import com.project.onlineexam.Entity.Enums.Role;
import com.project.onlineexam.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "select u from User u where u.email = :email ")
    public User findByEmail(@Param("email") String email);

    @Query(value = "select u from User u where u.role = :role1")
    public List<User> getAllUsersByRole(@Param("role1") Role role1);

    @Query(value = "select u from User u where u.email = :email")
    public List<User> getUserByEmail(@Param("email") String email);
}
