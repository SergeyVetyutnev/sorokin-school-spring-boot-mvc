package dev.vetyutnev.sorokinschoolspringbootmvc.controllers;

import dev.vetyutnev.sorokinschoolspringbootmvc.entity.Pet;
import dev.vetyutnev.sorokinschoolspringbootmvc.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping()
    public ResponseEntity<Pet> createPet(@Valid @RequestBody Pet pet){
        Pet newPet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPet);
    }

    @GetMapping
    public ResponseEntity<List<Pet>> getAllPets(){
        List<Pet> pets = petService.getAllPets();
        return ResponseEntity.ok(pets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id){
        Pet pet = petService.getPetById(id);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Pet> updatePet(
//            @PathVariable Long id,
//            @Valid @RequestBody Pet pet
//    ){
//        Pet updatedPet = petService.updatePet(id, pet);
//    }
}
