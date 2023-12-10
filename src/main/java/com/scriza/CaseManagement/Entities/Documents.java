package com.scriza.CaseManagement.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Documents {

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private String documentId;

        private String fileName;
        private String fileType;
        private byte[] file;

        @Lob
        private byte[] qrCode;

        public String getDocumentId() {
                this.documentId = String.valueOf(UUID.randomUUID());
                return documentId;
        }

        public void setDocumentId(String documentId) {
                this.documentId = documentId;
        }
}
