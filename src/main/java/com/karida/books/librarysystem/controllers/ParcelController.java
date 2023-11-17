package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.models.Parcel;
import com.karida.books.librarysystem.repository.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/parcels")
public class ParcelController {

    @Autowired
    private ParcelRepository parcelRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getParcels(){
        try{
            List<Parcel> parcel= parcelRepository.findAll();
            if (parcel.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(parcel, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. ", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getParcelById(@PathVariable Long id) {
        try{
            Parcel parcel = parcelRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(parcel);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/data/{company_name}", method = RequestMethod.GET)
    public ResponseEntity<Object> getParcelByName(@PathVariable String company_name) {
        try{
            Parcel parcel = parcelRepository.finByName(company_name);
            if (parcel != null) {
                return new ResponseEntity<>(parcel, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertParcel(@RequestBody Parcel newparcel) {
        try{
            String company_name = newparcel.getCompany_name();
            if (verificationIfParcelExistByName(company_name)) {
                return new ResponseEntity<>("Parcel already exist", HttpStatus.CONFLICT);
            } else {
                parcelRepository.save(newparcel);
                return new ResponseEntity<>("Insert successful", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public boolean verificationIfParcelExistByName(@PathVariable String company_name) {
        Parcel parcel = parcelRepository.finByName(company_name);
        return parcel != null;
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateParcel(@PathVariable Long id, @RequestBody Parcel parcelRequest){
        Object[] parcelDataToVerifyDuplicated;
        try{
            Parcel parcel = parcelRepository.findById(id).orElseThrow();
            parcelDataToVerifyDuplicated = verificationIfParcelExistByName(parcelRequest.getCompany_name(), id);
            if((boolean) parcelDataToVerifyDuplicated[0]){
                return new ResponseEntity<>("Parcel already exist (Company Name)", HttpStatus.CONFLICT);
            }else{
                return putOperation(parcel, parcelRequest, 1);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public Object[] verificationIfParcelExistByName(@PathVariable String company_name, @PathVariable Long id_parcel){
        Object[] parcelDataQuery = new Object[2];
        Parcel parcel = parcelRepository.findTheNameInOtherCompanies(company_name, id_parcel);
        if (parcel != null){
            parcelDataQuery[0] = true;
            parcelDataQuery[1] = parcel;
        }else{
            parcelDataQuery[0] = false;
            parcelDataQuery[1] = null;
        }
        return parcelDataQuery;
    }
    @RequestMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteParcel(@PathVariable Long id, @RequestBody Parcel parcelRequest){
        try{
            Parcel parcel = parcelRepository.findById(id).orElseThrow();
            return putOperation(parcel, parcelRequest, 2);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable Parcel parcel, Parcel parcelRequest, int type) {
        parcel.setCompany_name(parcelRequest.getCompany_name());
        parcel.setManager(parcelRequest.getManager());
        parcel.setAddress_p(parcelRequest.getAddress_p());
        parcel.setEmail(parcelRequest.getEmail());
        parcel.setStatus_c(parcelRequest.getStatus_c());
        parcelRepository.save(parcel);
        if(type == 1) {
            return new ResponseEntity<>("Update finished", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
        }
    }
}
