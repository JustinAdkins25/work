package com.example.demo.controller;

import com.example.demo.Repository.UserRepository;
import com.example.demo.ResourceNotFoundException.ResourceNotFoundException;
import com.example.demo.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){

        this.userRepository = userRepository;
    }


    @GetMapping("/users")
    public List<User> list(){

        return this.userRepository.findAll();
    }
    @GetMapping("/users/{Id}")
    public ResponseEntity<User> getUserById(@PathVariable Long Id) {
        User users = userRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + Id));
        return ResponseEntity.ok(users);
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user){

        return this.userRepository.save(user);
    }
    @DeleteMapping("/users/{Id}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@PathVariable Long Id){
        User users = this.userRepository.findById(Id).orElseThrow(() ->
                new ResourceNotFoundException("User not exist with id : " + Id));
        this.userRepository.deleteById(Id);
        Map<String,Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/users/{Id}")
    public ResponseEntity<User> updateUser(@RequestBody User userInput,@PathVariable Long Id){
        User users = this.userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not exist with id : " + Id));
        users.setUserName(userInput.getUserName());
        users.setFirstName(userInput.getFirstName());
        users.setLastName(userInput.getLastName());
        User updateUser = userRepository.save(users);
        return ResponseEntity.ok(updateUser);

    }


}
