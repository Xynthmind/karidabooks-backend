package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.repository.AddressRepository;
import com.karida.books.librarysystem.models.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/addresses")
public class AddressController {
    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAddresses(){
        try{
            List<Address>  address= addressRepository.findAll();
            if (address.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(address, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/exist/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAddressById(@PathVariable Long id){
        try{
            Address address = addressRepository.findById(id).orElseThrow();
            return new ResponseEntity<>(address, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the address does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getAddressById_User(@PathVariable Long id){
        try {
            List<Address> address = addressRepository.findById_User(id);
            if(address.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            }else{
                return new ResponseEntity<>(address, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertAddress(@RequestBody Address address){
        try{
            addressRepository.save(address);
            return new ResponseEntity<>("Insert successful", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateAddress(@PathVariable Long id, @RequestBody Address newaddress){
        try{
            Address address = addressRepository.findById(id).orElseThrow();
            address.setId_user(newaddress.getId_user());
            address.setCountry(newaddress.getCountry());
            address.setStreet(newaddress.getStreet());
            address.setHouse_number(newaddress.getHouse_number());
            address.setZc(newaddress.getZc());
            address.setDelivery_instructions(newaddress.getDelivery_instructions());
            addressRepository.save(address);
            return new ResponseEntity<>("Update successful", HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAddress(@PathVariable Long id) {
        try{
            addressRepository.findById(id).orElseThrow();
            addressRepository.deleteById(id);
            return new ResponseEntity<>("Delete successful", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than address does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
//    End of class
}
