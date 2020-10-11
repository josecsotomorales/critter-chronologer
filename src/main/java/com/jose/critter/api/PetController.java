package com.jose.critter.api;

import com.jose.critter.dto.PetDTO;
import com.jose.critter.entity.Customer;
import com.jose.critter.entity.Pet;
import com.jose.critter.service.CustomerService;
import com.jose.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        Pet pet = this.petService.save(convertToPet(petDTO));
        this.customerService.addPetToOwner(pet, petDTO.getOwnerId());
        return convertToPetDTO(pet);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        return convertToPetDTO(this.petService.findById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        return this.petService
                .findAll()
                .stream()
                .map(PetController::convertToPetDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        return this.petService
                .findPetByOwnerId(ownerId)
                .stream()
                .map(PetController::convertToPetDTO)
                .collect(Collectors.toList());
    }

    // DTO Converters
    private static PetDTO convertToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        if (pet.getCustomer() != null) {
            petDTO.setOwnerId(pet.getCustomer().getId());
        }
        return petDTO;
    }

    private static Pet convertToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        if (petDTO.getOwnerId() != 0) {
            Customer owner = new Customer();
            owner.setId(petDTO.getOwnerId());
            pet.setCustomer(owner);
        }
        BeanUtils.copyProperties(petDTO, pet);
        return pet;
    }
}
