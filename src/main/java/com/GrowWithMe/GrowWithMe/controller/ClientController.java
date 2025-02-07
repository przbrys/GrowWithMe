package com.GrowWithMe.GrowWithMe.controller;

import com.GrowWithMe.GrowWithMe.model.Client;
import com.GrowWithMe.GrowWithMe.model.Trainer;
import com.GrowWithMe.GrowWithMe.model.User;
import com.GrowWithMe.GrowWithMe.service.impl.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>> getAllClient() {
        List<Client> clientList = clientService.getAllClient();
        return new ResponseEntity<>(clientList, clientList.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Integer id) {
        Optional<Client> clientOptional = clientService.getClientById(id);
        return clientOptional.map(client -> new ResponseEntity<>(client, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/clientTrainer")
    public ResponseEntity<Trainer> getClientTrainer(@PathVariable Integer id) {
        Optional<Trainer> trainerOptional = clientService.getClientTrainer(id);
        return trainerOptional.map(trainer -> new ResponseEntity<>(trainer, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClientEntity(@PathVariable Integer id) {
        try {
            clientService.deleteClientEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Client> createClientEntity(@RequestBody Client client) {
        try {
            Client clientToCreate = clientService.createClientEntity(client);
            return new ResponseEntity<>(clientToCreate, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client clientToUpdate) {
        try {
            Client updatedClient = clientService.updateClient(clientToUpdate);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/clientInfoFromUser")
    public ResponseEntity<User> getClientInfoFromUser(@PathVariable Integer id) {
        Optional<User> userOptional = clientService.getClientInfoFromUser(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
