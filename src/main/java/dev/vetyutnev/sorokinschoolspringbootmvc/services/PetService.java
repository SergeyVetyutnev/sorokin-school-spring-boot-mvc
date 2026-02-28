package dev.vetyutnev.sorokinschoolspringbootmvc.services;

import dev.vetyutnev.sorokinschoolspringbootmvc.entity.Pet;
import dev.vetyutnev.sorokinschoolspringbootmvc.entity.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class PetService {

    Map<Long, Pet> pets = new HashMap<>();
    Long nextId = 0L;

    private final UserService userService;

    public PetService(UserService userService) {
        this.userService = userService;
    }

    public Pet createPet(Pet pet) {

        User owner = userService.getUserById(pet.getUserId());

        Pet newPet = new Pet(
                ++nextId,
                pet.getName(),
                pet.getUserId()
        );

        pets.put(nextId, newPet);
        owner.getPets().add(newPet);

        return newPet;
    }

    public List<Pet> getAllPets() {
        return pets.values().stream().toList();
    }

    public Pet getPetById(Long id) {
        Pet pet = pets.get(id);
        if (pet == null){
            throw new NoSuchElementException("питомец с id %s не найден".formatted(id));
        }
        return pet;
    }

//    public Pet updatePet(Long id, Pet pet) {
//
//    }
}
