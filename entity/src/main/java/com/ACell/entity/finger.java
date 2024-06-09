package com.ACell.entity;

import javax.persistence.*;

public class finger {
    @Entity
    public class Fingerprint {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @Lob
        private byte[] fingerprintData;

        // getters and setters
    }
}
