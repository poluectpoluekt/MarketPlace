package com.ed.orderservice.entity;


import com.ed.orderservice.entity.enums.StatusInvoice;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "Invoice")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_order")
    @SequenceGenerator(name = "sequence_order", sequenceName = "order_main_sequence", allocationSize = 1)
    @Column(name = "order_id")
    private long invoiceId;

    @Column(name = "id_MarketplaceService")
    private long idInMarketplaceService;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_invoice")
    private StatusInvoice invoiceStatus;

    @Column(name = "email_owner")
    private String ownerEmail;

    @CreatedDate
    @Column(name = "create_date")
    private Instant invoiceDate;


}
