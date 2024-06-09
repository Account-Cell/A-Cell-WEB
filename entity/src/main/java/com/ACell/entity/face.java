package com.ACell.entity;

import javax.persistence.*;

public class face {
    @Entity
    public class Face {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @Lob
        private byte[] faceData;

        // getters and setters
    }
}
