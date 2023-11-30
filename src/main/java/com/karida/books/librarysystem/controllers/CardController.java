package com.karida.books.librarysystem.controllers;

import com.karida.books.librarysystem.repository.CardRepository;
import com.karida.books.librarysystem.models.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/library/cards")
public class CardController {
    @Autowired
    private CardRepository cardRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getCards(){
        try{
            List<Card> card= cardRepository.findAll();
            if (card.isEmpty()){
                return new ResponseEntity<>("There are nothing in DB", HttpStatus.INTERNAL_SERVER_ERROR);
            }else{
                return new ResponseEntity<>(card, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. ", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/exist/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCardById(@PathVariable Long id) {
        try{
            Card card = cardRepository.findById(id).orElseThrow();
            return ResponseEntity.ok(card);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the card does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity< List<Card>> getCardByIdUser(@PathVariable Long id) {
        try{
            List<Card> card = cardRepository.findById_User(id);
            if (card.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.CONFLICT);
            } else {
                return new ResponseEntity<>(card, HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    } //remember verify the error code in the front

    @RequestMapping(value = "/data/{card_number}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCardByCardNumber(@PathVariable Long card_number) {
        try{
            Card card = cardRepository.findByCard_number(card_number);
            if (card != null) {
                return new ResponseEntity<>(card, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The card does not exist", HttpStatus.CONFLICT);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than the card does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> insertCard(@RequestBody Card newcard) {
        try{
            Long card_number = newcard.getCard_number();
            if (verificationCardExistInDB(card_number)) {
                return new ResponseEntity<>("Card already exist", HttpStatus.CONFLICT);
            } else {
                cardRepository.save(newcard);
                return new ResponseEntity<>("Insert successful", HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Our team has been notified and is actively working to resolve the issue. Thank you for your patience.", HttpStatus.EXPECTATION_FAILED);
        }
    }
    public boolean verificationCardExistInDB(@PathVariable Long card_number) {
        Card card = cardRepository.findByCard_number(card_number);
        return card != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteCard(@PathVariable Long id) {
        try{
            cardRepository.findById(id).orElseThrow();
            cardRepository.deleteById(id);
            return new ResponseEntity<>("Delete successful", HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("An unexpected error has occurred. We apologize for the inconvenience. " +
                    "Is possible than card does not exist", HttpStatus.EXPECTATION_FAILED);
        }
    }
}
