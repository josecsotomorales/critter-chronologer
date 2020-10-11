package com.jose.critter.repository;

import com.jose.critter.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findAllByPetIds(Long petId);
    List<Schedule> findAllByEmployeeIds(Long employeeId);
    List<Schedule> findAllByPetIdsIn(Set<Long> petIds);

}
