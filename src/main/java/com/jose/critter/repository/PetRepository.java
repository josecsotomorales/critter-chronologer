package com.jose.critter.repository;

import com.jose.critter.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findAllByCustomerId(Long customerId);

}
