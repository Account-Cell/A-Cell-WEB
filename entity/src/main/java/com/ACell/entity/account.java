package com.ACell.entity;

import javax.persistence.*;
import java.util.Date;

public class account {
    @Entity
    public class Account {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @Column(unique = true, nullable = false)
        private String accountNumber;

        @Column(nullable = false)
        private String accountPassword;

        @Temporal(TemporalType.TIMESTAMP)
        private Date accountOpenDate;

        private Double balance;

        // getters and setters
    }

}
