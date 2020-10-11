package com.jose.critter.entity;

import com.jose.critter.util.PetType;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Pet {

    // Attributes
    @Id
    @GeneratedValue
    private Long id;

    @Nationalized
    private String name;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String notes;

    // Relationships
    @ManyToOne(fetch = FetchType.LAZY) //many pets can belong to one customer
    @JoinColumn(name = "customer_id")  //map the join column in the pet table
    private Customer customer;

    // Constructors
    public Pet() {
    }

    public Pet(String name, LocalDate birthDate, PetType type, String notes, Customer customer) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = type;
        this.notes = notes;
        this.customer = customer;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

}
