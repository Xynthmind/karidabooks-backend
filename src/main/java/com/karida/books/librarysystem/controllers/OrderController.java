package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.models.Order;
import com.karida.books.librarysystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/library/orders")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getOrders(){
        try{
            List<Order> order= orderRepository.findAll();
            if (order.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. ", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
        try{
            Order order = orderRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(order);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the order does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getOrdersByIdUser(@PathVariable Long id) {
        try{
            List<Order> order = orderRepository.findById_User(id);
            if (order.isEmpty()) {
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. " +
                    "We apologize for the inconvenience.", HttpStatus.EXPECTATION_FAILED);
        }
    } //remember verify the error code in the front
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertOrder(@RequestBody Order neworder) {
        try{
                orderRepository.save(neworder);
                return new ResponseEntity<>("Insert successful", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateOrder(@PathVariable Long id, @RequestBody Order orderRequest){
        try{
            Order order = orderRepository.findById(id).orElseThrow();
            return putOperation(order, orderRequest, 1);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the order does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(value = "/del/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id, @RequestBody Order orderRequest){
        try{
            Order order = orderRepository.findById(id).orElseThrow();
            return putOperation(order, orderRequest, 2);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the order does not exist.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public ResponseEntity<String> putOperation(@PathVariable Order order, Order orderRequest, int type) {
        order.setId_user(orderRequest.getId_user());
        order.setId_card(orderRequest.getId_card());
        order.setId_address(orderRequest.getId_address());
        order.setId_parcel(orderRequest.getId_parcel());
        order.setOrder_date(orderRequest.getOrder_date());
        order.setShipping_date(orderRequest.getShipping_date());
        order.setArrive_date(orderRequest.getArrive_date());
        order.setTotal(orderRequest.getTotal());
        order.setOrder_status(orderRequest.getOrder_status());
        order.setStatus_c(orderRequest.getStatus_c());
        orderRepository.save(order);
        if(type == 1) {
            return new ResponseEntity<>("Update finished", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Logic delete successful", HttpStatus.OK);
        }
    }
}
