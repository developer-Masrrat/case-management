package com.scriza.CaseManagement.Services;

import com.google.zxing.WriterException;
import com.scriza.CaseManagement.Entities.Cases;
import com.scriza.CaseManagement.Entities.Clients;
import com.scriza.CaseManagement.Entities.Documents;
import com.scriza.CaseManagement.Entities.Tasks;
import com.scriza.CaseManagement.Exceptions.CaseNotFoundException;
import com.scriza.CaseManagement.GenerateUtil.QRCodeGenerator;
import com.scriza.CaseManagement.Repositories.CasesRepo;
import com.scriza.CaseManagement.Repositories.ClientsRepo;
import com.scriza.CaseManagement.Repositories.TasksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CasesService {

    private final CasesRepo casesRepo;
    private final ClientsRepo clientsRepo;

    private final TasksRepo tasksRepo;

    @Autowired
    public CasesService(CasesRepo casesRepo, ClientsRepo clientsRepo, TasksRepo tasksRepo) {
        this.casesRepo = casesRepo;
        this.clientsRepo = clientsRepo;
        this.tasksRepo = tasksRepo;
    }

    public Cases registerCases(Cases newCases, String clientId){
        Optional<Clients> client = clientsRepo.findById(clientId);
        if (client.isPresent()) {
            return casesRepo.save(newCases);
        }
        return null;
    }



//
//    public Documents uploadFile(String caseNumber, MultipartFile file) throws IOException , WriterException {
//        Cases existingCase = casesRepo.findById(caseNumber).orElse(null);
//
//        if (existingCase != null && !file.isEmpty()) {
//            Documents document = new Documents();
//            document.setFileName(file.getOriginalFilename());
//            document.setFileType(file.getContentType());
//            document.setFile(file.getBytes());
//            existingCase.getDocuments().add(document);
//
//            casesRepo.save(existingCase);
//
//
//            return document;
//        }
//
//        return null; // Handle case not found or empty file
//    }
//
//    public byte[] getQrCode(String caseNumber, String documentId) {
//        Cases existingCase = casesRepo.findById(caseNumber).orElse(null);
//
//        if (existingCase != null) {
//            for (Documents document : existingCase.getDocuments()) {
//                if (document.getDocumentId().equals(documentId)) {
//                    // Generate QR code for document data
//                    String qrCodeData = "File Name: " + document.getFileName() + "\nFile Type: " + document.getFileType();
//                    try {
//                        return QRCodeGenerator.generateQRCode(qrCodeData);
//                    } catch (WriterException | IOException e) {
//                        // Handle QR code generation error
//                        e.printStackTrace();
//                        return null;
//                    }
//                }
//            }
//        }
//
//        return null; // Document or case not found
//    }
    public Optional<Cases> getCaseByCaseNumber(String caseNumber){
        Optional<Cases> cases = casesRepo.findById(caseNumber);
        if (cases.isEmpty()) {
            throw new CaseNotFoundException("No Case found with CaseNUmber " +caseNumber);
        }
        return cases;

    }
    public List<Cases> getCaseByClientId(String clientId){
        List<Cases> cases = casesRepo.findCaseByClientId(clientId);
        return cases;
    }

        public Cases updateCases(String caseNumber, Cases cases) {
        Cases existingCases =casesRepo.findById(caseNumber)
                .orElseThrow(() -> new CaseNotFoundException("No Case found with CaseNUmber " +caseNumber));

        existingCases.setCaseName(cases.getCaseName());
        existingCases.setCaseDescriptions(cases.getCaseDescriptions());
        existingCases.setStatus(cases.getStatus());
        existingCases.setAddNotes(cases.getAddNotes());


        return casesRepo.save(existingCases);
    }


//    public void deleteCases(String caseNumber) {
//        if (casesRepo.existsById(caseNumber)) {
//            casesRepo.deleteById(caseNumber);
//        } else {
//            throw new CaseNotFoundException("Case with Number " + caseNumber + " not found");
//        }
//    }

        public void deleteCaseWithAllTasks(String caseNumber){
            Optional<Cases> cases = casesRepo.findById(caseNumber);
            if (cases.isPresent()) {
            Cases case1 = cases.get();
            List<Tasks> tasks = tasksRepo.findTaskByCaseNumber(caseNumber);
            tasks.forEach(tasksRepo::delete);
            casesRepo.delete(case1);

        }

}
}

