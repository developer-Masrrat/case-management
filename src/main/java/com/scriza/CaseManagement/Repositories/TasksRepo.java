package com.scriza.CaseManagement.Repositories;

import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepo extends JpaRepository<Tasks,String> {
//    Tasks findTaskByCaseId(String caseId);
List<Tasks> findTaskByCaseNumber(String caseNUmber);

}
