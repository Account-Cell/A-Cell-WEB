package com.ACell.entity;

import javax.persistence.*;
import java.util.Date;

public class transaction {
    @Entity
    public class Transaction {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "customer_id", nullable = false)
        private Customer customer;

        @ManyToOne
        @JoinColumn(name = "account_id", nullable = false)
        private Account account;

        private Double transactionAmount;

        @Temporal(TemporalType.TIMESTAMP)
        private Date transactionDate;

        private String transactionType;

        // getters and setters
    }
}
