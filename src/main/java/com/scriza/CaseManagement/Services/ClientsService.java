package com.scriza.CaseManagement.Services;

import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Clients;
import com.scriza.CaseManagement.Exceptions.ClientNotFoundException;
import com.scriza.CaseManagement.Repositories.CasesRepo;
import com.scriza.CaseManagement.Repositories.ClientsRepo;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    private final ClientsRepo clientsRepo;
    private final CasesRepo casesRepo;

    @Autowired
    public ClientsService(ClientsRepo clientsRepo, CasesRepo casesRepo) {
        this.clientsRepo = clientsRepo;
        this.casesRepo = casesRepo;
    }

    public ResponseEntity<?> registerUser(Clients newUser){

        clientsRepo.save(newUser);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message:","Case registered Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject);
    }

    public Optional<Clients> getUserById(String id){
        Optional<Clients> clients = clientsRepo.findById(id);
        if (clients.isEmpty()) {
            throw new ClientNotFoundException("No clients found with id " +id);
        }
        return clients;

    }
    public List<Clients> getAllClients() {
        return clientsRepo.findAll();
    }

    public Clients updateClients(String id, Clients clients) {
        Clients existingUser = clientsRepo.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("User with ID " + id + " not found"));

        existingUser.setName(clients.getName());
        existingUser.setAddress(clients.getAddress());
        existingUser.setEmail(clients.getEmail());
        existingUser.setPhoneNumber(clients.getPhoneNumber());

        return clientsRepo.save(existingUser);
    }
    @Transactional
    public void deleteUserWithCases(String clientId) {
        Optional<Clients> clientOptional = clientsRepo.findById(clientId);

        if (clientOptional.isPresent()) {
            Clients user = clientOptional.get();
            List<Cases> cases = casesRepo.findCaseByClientId(clientId);

            // Delete cases associated with the user
            cases.forEach(casesRepo::delete);

            // Delete the user
            clientsRepo.delete(user);
        }
    }
//    public void deleteUser(String id) {
//        if (usersRepo.existsById(id)) {
//
//            usersRepo.deleteById(id);
//        } else {
//            throw new UserNotFoundException("User with ID " + id + " not found");
//        }
//    }


}
