package com.scriza.CaseManagement.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "cases")
public class Cases {

        @Id
        @Column(name = "case_number")
        @GeneratedValue(strategy = GenerationType.UUID)
        private String caseNumber;
        private String caseName;
        private String caseDescriptions;
        private String addNotes;
        private String status;
        private  String clientId;
        private String documentId;
        @Transient
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "client_profile")
        private Clients clients;
        @Transient
        @OneToMany(mappedBy = "case", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Tasks> tasks;
        @Transient
        @OneToMany(mappedBy = "documents", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Documents> documents;

        public Cases() {
                this.documents = new ArrayList<>();
        }

    }

