package com.jose.critter.repository;

import com.jose.critter.entity.Employee;
import com.jose.critter.util.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findDistinctByDaysAvailableAndSkillsIn(DayOfWeek dayOfWeek, Set<EmployeeSkill> employeeSkills);

}
