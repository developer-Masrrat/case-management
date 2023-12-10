package com.scriza.CaseManagement.Repositories;

import com.scriza.CaseManagement.Entities.Cases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasesRepo extends JpaRepository<Cases,String> {


    List<Cases> findCaseByClientId(String clientId);
    Cases deleteCaseByClientId(String clientId);


}
