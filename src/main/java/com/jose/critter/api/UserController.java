package com.jose.critter.api;

import com.jose.critter.dto.CustomerDTO;
import com.jose.critter.dto.EmployeeDTO;
import com.jose.critter.dto.EmployeeRequestDTO;
import com.jose.critter.entity.Customer;
import com.jose.critter.entity.Employee;
import com.jose.critter.entity.Pet;
import com.jose.critter.service.CustomerService;
import com.jose.critter.service.EmployeeService;
import com.jose.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PetService petService;

    public UserController(CustomerService customerService, EmployeeService employeeService, PetService petService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.petService = petService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return convertToCustomerDTO(
                this.customerService.saveCustomer(convertToCustomer(customerDTO))
        );
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        return this.customerService
                .getAllCustomers()
                .stream()
                .map(this::convertToCustomerDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{id}")
    public CustomerDTO getCustomerById(@PathVariable long id){
        return convertToCustomerDTO(
                this.customerService.getCustomer(id)
        );
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return convertToCustomerDTO(
                this.petService.findById(petId).getCustomer()
        );
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return convertToEmployeeDTO(
                this.employeeService.saveEmployee(convertToEmployee(employeeDTO))
        );
    }

    @GetMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return convertToEmployeeDTO(
                this.employeeService.getEmployee(employeeId)
        );
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        this.employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return this.employeeService
                .getAllEmployeesRequest(employeeRequestDTO)
                .stream()
                .map(this::convertToEmployeeDTO)
                .collect(Collectors.toList());
    }

    // DTO Converters
    private CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setPetIds(new ArrayList<>());
        BeanUtils.copyProperties(customer, customerDTO);

        if (customer.getPets() != null && !customer.getPets().isEmpty()) {
            for (Pet pet : customer.getPets()) {
                customerDTO.getPetIds().add(pet.getId());
            }
        }
        return customerDTO;
    }

    private Customer convertToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(new ArrayList<>());

        if (customerDTO.getPetIds() != null && !customerDTO.getPetIds().isEmpty()) {
            for (Long petId : customerDTO.getPetIds()) {
                Pet newPet = new Pet();
                newPet.setId(petId);
                customer.getPets().add(newPet);
            }
        }
        return customer;
    }

    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        return employeeDTO;
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        return employee;
    }
}
