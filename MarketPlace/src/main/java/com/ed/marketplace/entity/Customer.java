package com.ed.marketplace.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Table(name = "Customer")
@NoArgsConstructor
@Entity
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_customer")
    @SequenceGenerator(name = "sequence_customer", sequenceName = "customer_main_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "User_Role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @CreatedDate
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Instant creationDate;

    @Column(name = "is_account_non_expired")
    private boolean isAccountNonExpired;

    @Column(name = "is_account_non_locked")
    private boolean isAccountNonLocked;

    @Column(name = "is_credentials_non_expired")
    private boolean isCredentialsNonExpired;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @ManyToMany
    @JoinTable(
            name = "Customer_Item",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> basketItemsAddedBeforePurchase = new ArrayList<>();

    @OneToMany(mappedBy = "customerOwner")
    private List<Order> ordersHistory = new ArrayList<>();

}
