package com.scriza.CaseManagement.Controllers;

import com.scriza.CaseManagement.Entities.Clients;
import com.scriza.CaseManagement.Exceptions.ClientNotFoundException;
import com.scriza.CaseManagement.Services.ClientsService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class ClientsController {

    private final ClientsService clientsService;

    @Autowired
    public ClientsController(ClientsService clientsService) {
        this.clientsService = clientsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Clients clients) {
        ResponseEntity<?> registeredUser = clientsService.registerUser(clients);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message:","User created Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject +"User ID: "+registeredUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            Optional<Clients> foundUser = clientsService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(foundUser);
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Clients>> getAllClients() {
        List<Clients> allClients = clientsService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateClients(@PathVariable String id, @RequestBody Clients updateClients) {
        try {
            Clients updated = clientsService.updateClients(id,updateClients);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","User updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserWithCases(@PathVariable String userId) {
        clientsService.deleteUserWithCases(userId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message:","User and associated cases deleted successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
    }

}
