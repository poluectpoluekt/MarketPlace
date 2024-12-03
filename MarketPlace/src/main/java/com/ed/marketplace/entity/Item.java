package com.ed.marketplace.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Table(name = "Item")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_item")
    @SequenceGenerator(name = "item_customer", sequenceName = "item_main_sequence", allocationSize = 1)
    @Column(name = "item_id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private BigDecimal priceItem;

    @Column(name = "amount_on_warehouse")
    private int itemOnWarehouse;

//    @ManyToMany
//    @JoinTable(
//            name = "Order_Item",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id")
//    )
//    private List<Order> itemInCustomerOrder;

//    @ManyToMany
//    @JoinTable(
//            name = "Customer_Item",
//            joinColumns = @JoinColumn(name = "customer_id"),
//            inverseJoinColumns = @JoinColumn(name = "item_id")
//    )
//    private List<Customer> itemInCustomerBasket;
}
