package com.jose.critter.service;

import com.jose.critter.entity.Pet;
import com.jose.critter.entity.Schedule;
import com.jose.critter.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public Schedule createSchedule(Schedule schedule) {
        return this.scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return this.scheduleRepository.findAll();
    }

    public List<Schedule> getAllPetSchedules(Long petId) {
        return this.scheduleRepository.findAllByPets(petId);
    }

    public List<Schedule> getAllEmployeeSchedules(Long employeeId) {
        return this.scheduleRepository.findAllByEmployees(employeeId);
    }

    public List<Schedule> getAllPetsSchedules(List<Pet> pets) {
        return this.scheduleRepository.findAllByPetsIn(pets);
    }
}
