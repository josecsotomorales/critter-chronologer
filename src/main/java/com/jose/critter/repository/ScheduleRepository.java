package com.jose.critter.repository;

import com.jose.critter.entity.Employee;
import com.jose.critter.entity.Pet;
import com.jose.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPets(Long petId);
    List<Schedule> findAllByEmployees(Long employeeId);
    List<Schedule> findAllByPetsIn(List<Pet> pets);

}