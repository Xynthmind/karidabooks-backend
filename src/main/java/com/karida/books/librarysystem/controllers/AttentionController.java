package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.models.Attention;
import com.karida.books.librarysystem.repository.AttentionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/attentions")
public class AttentionController {
    @Autowired
    private AttentionRepository attentionRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAttentions(){
        try{
            List<Attention>  attention = attentionRepository.findAll();
            if (attention.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(attention, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertAddress(@RequestBody Attention attention){
        try{
            attentionRepository.save(attention);
            return new ResponseEntity<>("Insert successful", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Attention newattention){
        try{
            Attention attention = attentionRepository.findById(id).orElseThrow();
            attention.setId_user(newattention.getId_user());
            attention.setDescription(newattention.getDescription());
            attention.setReq_type(newattention.getReq_type());
            attention.setAtt_date(newattention.getAtt_date());
            attention.setAtt_time(newattention.getAtt_time());
            attention.setStatus_c(newattention.getStatus_c());
            attentionRepository.save(attention);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
