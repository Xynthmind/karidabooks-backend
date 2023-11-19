package com.karida.books.librarysystem.controllers;
import com.karida.books.librarysystem.models.OrderDetails;
import com.karida.books.librarysystem.repository.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin
@RequestMapping("/library/ordersdetails")
public class OrderDetailsController {
    @Autowired
    private OrderDetailsRepository orderDetailsRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getOrdersDetails(){
        try{
            List<OrderDetails> orderDetails= orderDetailsRepository.findAll();
            if (orderDetails.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(orderDetails, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. ", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderDetailsById(@PathVariable Long id) {
        try{
            OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(orderDetails);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the order does not have details to show", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/order/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderDetailsByOrder(@PathVariable Long id) {
        try{
            List<OrderDetails> orderDetails = orderDetailsRepository.finByOrder(id);
            if (orderDetails.isEmpty()) {
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(orderDetails, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertOrderDetails(@RequestBody List<OrderDetails> neworderDetails ) {
        try{
                orderDetailsRepository.saveAll(neworderDetails);
                return new ResponseEntity<>("Insert successful", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateOrderDetails(@PathVariable Long id, @RequestBody OrderDetails orderDetailsRequest){
        try{
            OrderDetails orderDetails = orderDetailsRepository.findById(id).orElseThrow();

            return putOperation(orderDetails, orderDetailsRequest);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the book does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable OrderDetails orderDetails, OrderDetails orderDetailsRequest) {
        orderDetails.setId_order(orderDetailsRequest.getId_order());
        orderDetails.setId_book(orderDetailsRequest.getId_book());
        orderDetails.setAmount(orderDetailsRequest.getAmount());
        orderDetails.setTotal_objects(orderDetailsRequest.getTotal_objects());
        orderDetailsRepository.save(orderDetails);
        return new ResponseEntity<>("Update finished", HttpStatus.OK);

    }
}
