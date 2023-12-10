package com.scriza.CaseManagement.Services;

import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Tasks;
import com.scriza.CaseManagement.Exceptions.CaseNotFoundException;
import com.scriza.CaseManagement.Exceptions.TaskNotFoundException;
import com.scriza.CaseManagement.Repositories.CasesRepo;
import com.scriza.CaseManagement.Repositories.TasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServices {

    private final TasksRepo tasksRepo;
    private final CasesRepo casesRepo;

    @Autowired
    public TaskServices(TasksRepo tasksRepo, CasesRepo casesRepo) {
        this.tasksRepo = tasksRepo;
        this.casesRepo = casesRepo;
    }

    public Tasks registerTask(Tasks newTasks, String caseNumber) {
        Optional<Cases> caseOptional = casesRepo.findById(caseNumber);
        if (caseOptional.isPresent()) {
            return tasksRepo.save(newTasks);
        }
        return null;
    }

    public Optional<Tasks> getTask(String taskId) {
        Optional<Tasks> tasks = tasksRepo.findById(taskId);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No Task found.");
        }
        return tasks;

    }

    public List<Tasks> getAllTasks(String caseNumber) {
        Optional<Cases> caseOptional = casesRepo.findById(caseNumber);
        if (!caseOptional.isEmpty()) {
            return tasksRepo.findAll();
        }
      return null;
    }
        public Tasks updateTask (String taskId, Tasks tasks){
            Tasks existing = tasksRepo.findById(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("No Task found."));
            existing.setTaskDetails(tasks.getTaskDetails());
            existing.setCaseNumber(tasks.getCaseNumber());
            existing.setLastModification(tasks.getLastModification());


            return tasksRepo.save(existing);
        }

    public void delete(String taskId) {
        if (tasksRepo.existsById(taskId)) {
            tasksRepo.deleteById(taskId);
        } else {
            throw new TaskNotFoundException("Task not found");
        }
    }

    }

