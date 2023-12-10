package com.scriza.CaseManagement.Controllers;

import com.google.zxing.WriterException;
import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Documents;
import com.scriza.CaseManagement.Exceptions.CaseNotFoundException;
import com.scriza.CaseManagement.Services.CasesService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cases")
public class CasesController {
    private final CasesService casesService;

    @Autowired
    public CasesController(CasesService casesService) {
        this.casesService = casesService;
    }
    @PostMapping("/register/{clientId}")
    public ResponseEntity<?> register(@RequestBody Cases cases , @PathVariable String clientId) {
        Cases registered = casesService.registerCases(cases , clientId);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message:","Case registered Successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(jsonObject+ "Case ID: "+ registered.getCaseNumber());
    }
    @GetMapping("/{caseNumber}")
    public ResponseEntity<?> getCase(@PathVariable String caseNumber) {
        try {
            Optional<Cases> foundCase = casesService.getCaseByCaseNumber(caseNumber);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Case Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(foundCase);
        } catch (CaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // Finding Case By User ID
    @GetMapping("/allCases/{clientId}")
    public ResponseEntity<?> getCaseByClientId(@PathVariable String clientId){
        try {
           List<Cases> foundCase = casesService.getCaseByClientId(clientId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Case Found Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(foundCase);
        } catch (CaseNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @PutMapping("/update/{caseNumber}")
    public ResponseEntity<?> updateCase(@PathVariable String caseNumber ,  @RequestBody Cases cases) {
        try {
            Cases updated = casesService.updateCases(caseNumber,cases);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message:","Case updated Successfully");
            return ResponseEntity.status(HttpStatus.OK).body(jsonObject);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
        @DeleteMapping("/{caseNumber}")
         public ResponseEntity<?> deleteCase(@PathVariable String caseNumber) {
                casesService.deleteCaseWithAllTasks(caseNumber);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message:","Cases and associated Tasks deleted successfully.");
                return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
            }

//    @PostMapping("/{caseNumber}/add-documents")
//    public ResponseEntity<?> addDocumentsToCase(@PathVariable String caseNumber,
//                                                @RequestBody List<Documents> documents,
//                                                @RequestParam(value = "file") MultipartFile file) {
//        try {
//            Cases updatedCase = casesService.addDocumentsToCase(caseNumber,documents,file);
//            if (updatedCase != null) {
//                return ResponseEntity.ok("Documents added to case successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found with number: " + caseNumber);
//            }
//        }catch (IOException | WriterException e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file.");
//        }
//
//    }

//    @PostMapping("/{caseNumber}/upload")
//    public ResponseEntity<?> uploadFile(@PathVariable String caseNumber, @RequestParam(value = "file") MultipartFile file) {
//        try {
//            Documents uploadedDocument = casesService.uploadFile(caseNumber, file);
//            if (uploadedDocument != null) {
//                System.out.println("file tho dekhlo zara___________"+uploadedDocument.getFileName());
//                return ResponseEntity.ok("File uploaded successfully.");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Case not found with number: " + caseNumber);
//            }
//        } catch (IOException | WriterException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file.");
//        }
//    }


//  @GetMapping("/{caseNumber}/qr-code")
//    public ResponseEntity<?> getQrCode(@PathVariable String caseNumber, @RequestParam("documentId") String documentId) {
//        byte[] qrCodeImage = casesService.getQrCode(caseNumber, documentId);
//
//        if (qrCodeImage != null) {
//            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }

}

