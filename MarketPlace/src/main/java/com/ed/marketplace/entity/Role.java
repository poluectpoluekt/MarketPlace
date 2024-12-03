package com.ed.marketplace.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Role")
@Setter
@Getter
@NoArgsConstructor
@Entity
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_role")
    @SequenceGenerator(name = "sequence_role", sequenceName = "role_main_sequence", allocationSize = 1)
    private long id;

    @Column(name = "name")
    private String name;
}
