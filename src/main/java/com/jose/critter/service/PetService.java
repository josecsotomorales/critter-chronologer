package com.jose.critter.service;

import com.jose.critter.entity.Pet;
import com.jose.critter.entity.Schedule;
import com.jose.critter.exception.PetNotFoundException;
import com.jose.critter.repository.PetRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Pet save(Pet pet) {
        return this.petRepository.save(pet);
    }

    public Pet findById(Long id){
        return this.petRepository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> findAll() {
        return this.petRepository.findAll();
    }

    public List<Pet> findPetByOwnerId(Long id){
        return this.petRepository.findAllByCustomerId(id);
    }

    public void addSchedule(Schedule schedule, Long petId) {
        Pet pet = this.petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
        if (pet.getSchedules() == null) {
            pet.setSchedules(new ArrayList<>());
        }
        pet.getSchedules().add(schedule);
        this.save(pet);
    }

}
