package com.example.ecommerceweb.model.orders;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;

@Entity
@Data
@Table(name = "pending_orders")
public class PendingOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String productName;
    private String productPrice;
    private String productBranch;
    private String orderedQuantity;
    private String customerAddress;
    private String customerEmail;
    private String paymentType;
    private String totalAmount;
    private String confirmedOrPending;
    private String status;
    private String selledBy;
    private String completed;
}
