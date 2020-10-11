package com.jose.critter.api;

import com.jose.critter.dto.ScheduleDTO;
import com.jose.critter.entity.Employee;
import com.jose.critter.entity.Pet;
import com.jose.critter.entity.Schedule;
import com.jose.critter.service.CustomerService;
import com.jose.critter.service.EmployeeService;
import com.jose.critter.service.PetService;
import com.jose.critter.service.ScheduleService;
import org.assertj.core.util.Lists;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public ScheduleController(ScheduleService scheduleService,
                              CustomerService customerService,
                              EmployeeService employeeService,
                              PetService petService) {
        this.scheduleService = scheduleService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        Schedule schedule = this.scheduleService.createSchedule(convertToSchedule(scheduleDTO));

        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            this.employeeService.addSchedule(schedule, employeeId);
        }

        for (Long petId : scheduleDTO.getPetIds()) {
            this.petService.addSchedule(schedule, petId);
        }

        return convertToScheduleDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return this.scheduleService
                .getAllSchedules()
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return this.scheduleService
                .getAllPetSchedules(petId)
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return this.scheduleService
                .getAllEmployeeSchedules(employeeId)
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<Pet> customerPets = this.petService.findPetByOwnerId(customerId);

        List<Pet> pets = new ArrayList<>(customerPets);

        return this.scheduleService
                .getAllPetsSchedules(pets)
                .stream()
                .map(this::convertToScheduleDTO)
                .collect(Collectors.toList());
    }

    // DTO Converters
    private ScheduleDTO convertToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();

        scheduleDTO.setDate(schedule.getDate());
        scheduleDTO.setActivities(schedule.getActivities());

        scheduleDTO.setEmployeeIds(Lists.newArrayList());
        for (Employee employee : schedule.getEmployees()) {
            scheduleDTO.getEmployeeIds().add(employee.getId());
        }

        scheduleDTO.setPetIds(Lists.newArrayList());
        for (Pet pet : schedule.getPets()) {
            scheduleDTO.getPetIds().add(pet.getId());
        }

        return scheduleDTO;
    }

    private Schedule convertToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        schedule.setActivities(scheduleDTO.getActivities());
        schedule.setDate(scheduleDTO.getDate());

        schedule.setEmployees(new ArrayList<>());
        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            Employee employee = new Employee();
            employee.setId(employeeId);
            schedule.getEmployees().add(employee);
        }

        schedule.setPets(new ArrayList<>());
        for (Long petId : scheduleDTO.getPetIds()) {
            Pet pet = new Pet();
            pet.setId(petId);
            schedule.getPets().add(pet);
        }
        return schedule;
    }
}
