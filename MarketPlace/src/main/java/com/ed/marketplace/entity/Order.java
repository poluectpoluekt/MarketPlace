package com.ed.marketplace.entity;

import com.ed.marketplace.entity.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "Invoice")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {

    @Column(name = "order_id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_order")
    @SequenceGenerator(name = "sequence_order", sequenceName = "invoice_main_sequence", allocationSize = 1)
    private long id;

    @Column(name = "total_amount")
    private BigDecimal totalAmountOrder;

    @CreatedDate
    @Column(name = "create_date")
    private LocalDateTime orderDate;

    @ManyToMany
    @JoinTable(
            name = "Order_Item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> itemsOnOrder = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "customer_id")
    private Customer customerOwner;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
