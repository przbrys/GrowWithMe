package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.BodyInformation;
import com.GrowWithMe.GrowWithMe.service.impl.BodyInformationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bodyInformation")
public class BodyInformationController {
    @Autowired
    private BodyInformationService bodyInformationService;

    @GetMapping
    public ResponseEntity<List<BodyInformation>> getAllBodyInformation(){
        List<BodyInformation> bodyInformationList = bodyInformationService.getAllBodyInformation();
        return new ResponseEntity<>(bodyInformationList, bodyInformationList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BodyInformation> getBodyInformationById(@PathVariable Integer id){
        Optional<BodyInformation> bodyInformationOptional=bodyInformationService.getBodyInformationById(id);
        return bodyInformationOptional.map(bodyInformation -> new ResponseEntity<>(bodyInformation, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<BodyInformation> createBodyInformationEntity(@RequestBody BodyInformation bodyInformation){
        BodyInformation createdBodyInformationEntity= bodyInformationService.createBodyInformationEntity(bodyInformation);
        return new ResponseEntity<>(createdBodyInformationEntity,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBodyInformationEntity(@PathVariable Integer id){
        try {
            bodyInformationService.deleteBodyInformation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }

    @PatchMapping("/{id}/bodyTrainerAdditionalInformation")
    public ResponseEntity<BodyInformation> updateBodyTrainerAdditionalInformation(@RequestBody BodyInformation bodyTrainerAdditionalInformation){
        try {
            BodyInformation updatedBodInformation=bodyInformationService.updateBodyTrainerAdditionalInformation(bodyTrainerAdditionalInformation);
            return new ResponseEntity<>(updatedBodInformation, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}