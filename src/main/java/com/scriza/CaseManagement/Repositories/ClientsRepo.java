package com.scriza.CaseManagement.Repositories;

import com.scriza.CaseManagement.Entities.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepo extends JpaRepository<Clients, String> {

}
