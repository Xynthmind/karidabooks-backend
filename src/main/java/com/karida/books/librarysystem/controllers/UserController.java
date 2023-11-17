package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.repository.UserRepository;
import com.karida.books.librarysystem.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    private Object[] userDataToDoVerification = new Object[2];
    //Get all records
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Object> getUsers(){
        try{
            List<User> user= userRepository.findAll();
            if (user.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value="/{email}/{password}", method = RequestMethod.GET)
    public ResponseEntity<String> verifyUserExistenceAtLogin(@PathVariable String email, @PathVariable String password){
        try{
            userDataToDoVerification = verifyUserExistenceInDB(email);
            if((boolean) userDataToDoVerification[0]){
                User user = (User) userDataToDoVerification[1];
                if(verifyPasswordMatch(password, user.getPassword())){
                    return new ResponseEntity<>("User's password match", HttpStatus.OK);
                }else{
                    return new ResponseEntity<>("User's password does not match", HttpStatus.CONFLICT);
                }
            }else{
                return new ResponseEntity<>("User does not exist", HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public Object[] verifyUserExistenceInDB(@PathVariable String email){
        Object[] userDataTemp = new Object[2];
        User user = userRepository.findByEmail(email);
        if (user != null){
            userDataTemp[0] = true;
            userDataTemp[1] = user;
        }else{
            userDataTemp[0] = false;
            userDataTemp[1] = null;
        }
        return userDataTemp;
    }
    public boolean verifyPasswordMatch(@PathVariable String password, @PathVariable String passwordToCheck){
        return password.equals(passwordToCheck);
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertUser(@RequestBody  User newUser){
        try{
            String email = newUser.getEmail();
            userDataToDoVerification = verifyUserExistenceInDB(email);
            if((boolean) userDataToDoVerification[0]){
                return new ResponseEntity<>("User already exist", HttpStatus.CONFLICT);
            }else{
                userRepository.save(newUser);
                return new ResponseEntity<>("SigUp finished", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User userRequest){
        try{
            User user = userRepository.findById(id).orElseThrow();
            userDataToDoVerification = verifyUserExistenceInDB(userRequest.getEmail(), id);
            if((boolean) userDataToDoVerification[0]){
                return new ResponseEntity<>("Email already exist", HttpStatus.CONFLICT);
            }else{
                return putOperation(user, userRequest, 1);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the user does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public Object[] verifyUserExistenceInDB(@PathVariable String email, @PathVariable Long id_user){
        Object[] userDataTemp = new Object[2];
        User user = userRepository.findTheEmailInOtherUser(email, id_user);
            if (user != null){
                userDataTemp[0] = true;
                userDataTemp[1] = user;
            }else{
                userDataTemp[0] = false;
                userDataTemp[1] = null;
            }
            return userDataTemp;

    }
    @RequestMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id, @RequestBody User userRequest){
        try{
            User user = userRepository.findById(id).orElseThrow();
            return putOperation(user, userRequest, 2);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the user does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable User user, User userRequest, int type) {
        user.setId_rol(userRequest.getId_rol());
        user.setFirst_name(userRequest.getFirst_name());
        user.setMiddle_name(userRequest.getMiddle_name());
        user.setLast_name(userRequest.getLast_name());
        user.setPhone_number(userRequest.getPhone_number());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setStatus_c(userRequest.getStatus_c());
        userRepository.save(user);
        if(type == 1) {
            return new ResponseEntity<>("Update finished", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
        }
    }
}
