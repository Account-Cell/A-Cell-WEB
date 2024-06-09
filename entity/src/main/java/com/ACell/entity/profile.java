package com.ACell.entity;

import javax.persistence.*;
import java.util.Date;

public class profile {
    @Entity
    public class Customer {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String customerNumber;

        @Column(unique = true, nullable = false)
        private String ssn;

        @Column(nullable = false)
        private String name;

        @Column(nullable = false)
        private String password;

        @Column(nullable = false)
        private String phoneNumber;

        @Column(nullable = false)
        private String address;

        @Lob
        private byte[] fingerprintData;

        @Lob
        private byte[] faceData;

        @Temporal(TemporalType.TIMESTAMP)
        private Date joinDate;

        // getters and setters
    }
}
