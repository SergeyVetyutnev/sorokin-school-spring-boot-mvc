package dev.vetyutnev.sorokinschoolspringbootmvc.controllers;

import static org.junit.jupiter.api.Assertions.*;

import dev.vetyutnev.sorokinschoolspringbootmvc.dto.PetDto;
import dev.vetyutnev.sorokinschoolspringbootmvc.entity.Pet;
import dev.vetyutnev.sorokinschoolspringbootmvc.entity.User;
import dev.vetyutnev.sorokinschoolspringbootmvc.services.PetService;
import dev.vetyutnev.sorokinschoolspringbootmvc.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PetController.class)
class PetControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PetService petService;

    @Test
    @DisplayName("POST /api/pets должен вернуть 201 и питомца")
    void createPet_ShouldReturnCreatedWhenValid() throws Exception {
        PetDto inputPetDto = new PetDto("pet_name", 1L);
        Pet savedPet = new Pet(10L, "pet_name", 1L);

        when(petService.createPet(any(Pet.class))).thenReturn(savedPet);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPetDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.name").value("pet_name"));
    }

    @Test
    @DisplayName("POST /api/pets должен вернуть 400, если данные невалидны")
    void createPet_ShouldReturnBadRequestWhenInvalidData() throws Exception {
        Pet inputPet = new Pet(null, "pet_name", null);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputPet)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("GET /api/pets должен вернуть 200 и питомца")
    void getPetById_ShouldReturnPetIfExist() throws Exception {
        Long petId = 1L;

        Pet existingPet = new Pet(1L, "pet_name", 1L);

        when(petService.getPetById(petId)).thenReturn(existingPet);

        mockMvc.perform(get("/api/pets/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("pet_name"));
    }

    @Test
    @DisplayName("DELETE /api/pets/{id} - должен вызвать метод удаления в сервисе и вернуть 204")
    void deletePet_ShouldCallServiceAndReturnNoContent() throws Exception {
        Long petId = 1L;

        mockMvc.perform(delete("/api/pets/{id}", petId))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletePet(petId);
    }

}