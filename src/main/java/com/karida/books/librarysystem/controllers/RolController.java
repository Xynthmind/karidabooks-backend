package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.repository.RolRepository;
import com.karida.books.librarysystem.models.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/roles")
public class RolController {
    private boolean checkName;
    @Autowired
    private RolRepository rolRepository;

    //Get all records
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<Object> getRoles(){
        try{
            List<Rol> rol= rolRepository.findAll();
            if (rol.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(rol, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    //Insert a new record
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<String> saveRol(@RequestBody Rol rol){
        try{
            checkName = checkRolDuplicatedByName(rol.getRol_name());
            if (checkName){
                return new ResponseEntity<>("The rol already exist", HttpStatus.CONFLICT);
            }else{
                boolean checkNumber = checkRolDuplicatedByNumber(rol.getRol_number());
                if(checkNumber){
                    return new ResponseEntity<>("The rol number already exist", HttpStatus.CONFLICT);
                }else{
                    rolRepository.save(rol);
                    return new ResponseEntity<>("Query successful", HttpStatus.OK);
                }
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public Boolean checkRolDuplicatedByName(@PathVariable String rol_name){
        Rol rol = rolRepository.findByName(rol_name);
        return rol != null;
    }
    public Boolean checkRolDuplicatedByNumber(@PathVariable Integer rol_number){
        Rol rol = rolRepository.findByNumber(rol_number);
        System.out.println(rol);
        return rol != null;
    }
    //Get by ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRolById(@PathVariable Long id){
        try{
            Rol rol = rolRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(rol);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the rol does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    //Update
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateRol(@PathVariable Long id, @RequestBody Rol rolRequest){
        try{
            Rol rol = rolRepository.findById(id).orElseThrow();
            checkName = checkRolDuplicatedByName(rolRequest.getRol_name());
            if (checkName){
                return new ResponseEntity<>("The rol already exist", HttpStatus.CONFLICT);
            }else{
                return putOperation(rol, rolRequest, 1);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than rol does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    //Update
    @RequestMapping(value = "/del/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> deleteRol(@PathVariable Long id, @RequestBody Rol rolRequest){
        try{
            Rol rol = rolRepository.findById(id).orElseThrow();
            return putOperation(rol, rolRequest, 2);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than rol does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable Rol rol, Rol rolRequest, int type){
        rol.setRol_number(rolRequest.getRol_number());
        rol.setRol_name(rolRequest.getRol_name());
        rol.setStatus_c(rolRequest.getStatus_c());
        rolRepository.save(rol);
        if(type == 1){
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
        }
    }
}
