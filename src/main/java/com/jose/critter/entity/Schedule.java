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

    public Schedule(LocalDate date, Set<EmployeeSkill> activities, List<Employee> employees, List<Pet> pets) {
        this.date = date;
        this.activities = activities;
        this.employees = employees;
        this.pets = pets;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<EmployeeSkill> getActivities() {
        return activities;
    }

    public void setActivities(Set<EmployeeSkill> activities) {
        this.activities = activities;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
