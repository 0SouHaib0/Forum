package com.api.controller;

import com.api.model.Question;
import com.api.model.User;
import com.api.repository.QuestionRepository;
import com.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private QuestionRepository Qr;
    @Autowired
    private UserRepository ur;

    @GetMapping("/users")
    public ResponseEntity<?>getAllUsersNoBloquer(){
        List<User> users = ur.findByIsblockedFalse();
        if (users.size()>0){
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/users/{id}")
    public ResponseEntity<?>getUserPerId(@PathVariable String id){
        Optional<User> userOptional = ur.findById(id);
        if (userOptional.isPresent()){
            return new ResponseEntity<>(userOptional, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/users/count")
    public int getNumberusers(){
        int n= (int) ur.count();
        return n;
    }
    @PostMapping("/users/add")
    public ResponseEntity<?>addUser(@RequestBody User user){
        Optional<User> userop = ur.findByEmail(user.getEmail());
        if(userop.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            user.setCreatedAt(new Date(System.currentTimeMillis()));
            user.setUpdatedAt(new Date(System.currentTimeMillis()));
            user.setAdmin(false);
            user.setReported_answers(new ArrayList<>());
            user.setQuestions(new ArrayList<>());
            user.setAnswers(new ArrayList<>());
            user.setReported_questions(new ArrayList<>());
            ur.save(user);
            Optional<User> userOptional = ur.findById(user.getId());
            return new ResponseEntity<>(userOptional, HttpStatus.OK);
        }
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> userop = ur.findByEmailAndIdIsNotIn(user.getEmail(),id);
        if(userop.isPresent()){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            Optional<User> userOptional = ur.findById(id);
            if (userOptional.isPresent()) {
                User userup = userOptional.get();
                userup.setNom(user.getNom());
                userup.setEmail(user.getEmail());
                userup.setPrenom(user.getPrenom());
                userup.setUpdatedAt(new Date(System.currentTimeMillis()));
                ur.save(userup);
                return new ResponseEntity<>(userOptional, HttpStatus.OK);
            }else{
                return new ResponseEntity<>("NOU", HttpStatus.OK);
            }
        }
    }

    @PutMapping("/users/update/password/{id}")
    public ResponseEntity<?> updatePasswordUser(@PathVariable String id, @RequestBody User user) {
        Optional<User> userOptional = ur.findById(id);
        if (userOptional.isPresent()) {
            User userup = userOptional.get();
            userup.setPassword(user.getPassword());
            userup.setUpdatedAt(new Date(System.currentTimeMillis()));
            ur.save(userup);
            return new ResponseEntity<>(ur.findById(id), HttpStatus.OK);
        } else {

            //"Post not found with id = " + id
            return new ResponseEntity<>("UP-NON", HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/users/login/{email}/{password}")
    public ResponseEntity<?>login(@PathVariable String email,@PathVariable String password){

        Optional<User> userOptional= ur.findByEmailAndPasswordAndIsblockedFalse(email,password);
        if (userOptional.isPresent()){
            return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/users/questions/{id}")
    public ResponseEntity<?> getQuestionByUserId(@PathVariable String id){
        List <Question> question = Qr.findByUserId(id);
        if (question.size()>0){
            return new ResponseEntity<>(question, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no Questions yet", HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/users/bloquer/{id}")
    public ResponseEntity<?>bloquer(@PathVariable String id){
        User user = ur.findById(id).get();
        user.setIsblocked(true);
        ur.save(user);
        if (ur.findById(id).isPresent()){
            return new ResponseEntity<>("blocked", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("none", HttpStatus.NOT_FOUND);
        }

    }
    @GetMapping("/users/bloquer")
    public ResponseEntity<?>getAllUsersBloquer(){
        List<User> users = ur.findByIsblockedTrue();
        if (users.size()>0){
            return new ResponseEntity<List<User>>(users, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("no users", HttpStatus.NOT_FOUND);
        }

    }
    @PutMapping("/users/debloquer/{id}")
    public ResponseEntity<?>debloquer(@PathVariable String id){
        User user = ur.findById(id).get();
        user.setIsblocked(false);
        ur.save(user);
        if (ur.findById(id).isPresent()){
            return new ResponseEntity<>("unblocked", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("none", HttpStatus.NOT_FOUND);
        }
    }



}
