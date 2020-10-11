package com.jose.critter.entity;

import com.jose.critter.util.EmployeeSkill;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
public class Schedule {

    // Attributes
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @ElementCollection(targetClass = EmployeeSkill.class)
    private Set<EmployeeSkill> activities;

    // Relationships
    @ManyToMany(mappedBy = "schedules")
    private List<Employee> employees;

    @ManyToMany(mappedBy = "schedules")
    private List<Pet> pets;

    //Constructors
    public Schedule() {}


    // Getters and Setters

}
