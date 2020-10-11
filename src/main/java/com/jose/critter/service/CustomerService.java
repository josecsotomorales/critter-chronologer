package com.jose.critter.service;

import com.jose.critter.entity.Customer;
import com.jose.critter.entity.Pet;
import com.jose.critter.exception.CustomerNotFoundException;
import com.jose.critter.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomer(Long customerId) {
        return this.customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public void addPetToOwner(Pet newPet, Long ownerId) {
        Customer owner = this.customerRepository.findById(ownerId).orElseThrow(CustomerNotFoundException::new);
        owner.getPets().add(newPet);
        this.saveCustomer(owner);
    }

}
