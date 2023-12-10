package com.scriza.CaseManagement.Controllers;

import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Tasks;
import com.scriza.CaseManagement.Exceptions.CaseNotFoundException;
import com.scriza.CaseManagement.Exceptions.TaskNotFoundException;
import com.scriza.CaseManagement.Services.TaskServices;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TasksController {
    private final TaskServices taskServices;

    @Autowired
    public TasksController(TaskServices taskServices) {
        this.taskServices = taskServices;
    }
    @PostMapping("/register/{caseNumber}")
    public ResponseEntity<?> register(@RequestBody Tasks tasks , @PathVariable String caseNumber) {
        try{
            Tasks registered = taskServices.registerTask(tasks,caseNumber);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Task Associate to Case are registered Successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject+ "taskId: "+ registered.getTaskId());
        }catch (TaskNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTask(@PathVariable String taskId) {
        try {
            Optional<Tasks> found = taskServices.getTask(taskId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Case Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(found);
        } catch (CaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping("/all/{caseNumber}")
    public ResponseEntity<?> getAllTask(@PathVariable String caseNumber){
        try {
            List<Tasks> found = taskServices.getAllTasks(caseNumber);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Task Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(found);
        } catch (TaskNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/update/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable String taskId ,  @RequestBody Tasks tasks) {
        try {
            Tasks updated = taskServices.updateTask(taskId,tasks);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Tasks updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> delete(@PathVariable String taskId) {
        try {
            taskServices.delete(taskId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","task deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
        } catch (CaseNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

