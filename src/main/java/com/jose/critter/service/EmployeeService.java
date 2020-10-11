package com.jose.critter.service;

import com.jose.critter.dto.EmployeeRequestDTO;
import com.jose.critter.entity.Employee;
import com.jose.critter.entity.Schedule;
import com.jose.critter.exception.EmployeeNotFoundException;
import com.jose.critter.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    public Employee getEmployee(Long employeeId) {
        return this.employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    public void setEmployeeAvailability(Set<DayOfWeek> dayOfWeeks, Long employeeId){
        Employee employee = this.getEmployee(employeeId);
        employee.setDaysAvailable(dayOfWeeks);
        this.saveEmployee(employee);
    }

    public List<Employee> getAllEmployeesRequest(EmployeeRequestDTO employeeRequestDTO) {
        return this.employeeRepository.findDistinctByDaysAvailableAndSkillsIn(
                employeeRequestDTO.getDate().getDayOfWeek(),
                employeeRequestDTO.getSkills()
        );
    }

    public void addSchedule(Schedule schedule, Long employeeId) {
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
        if (employee.getSchedules() == null) {
            employee.setSchedules(new ArrayList<>());
        }
        employee.getSchedules().add(schedule);
        this.saveEmployee(employee);
    }

}
