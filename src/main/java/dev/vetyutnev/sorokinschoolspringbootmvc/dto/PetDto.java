package dev.vetyutnev.sorokinschoolspringbootmvc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetDto(
        @NotBlank(message = "имя не может быть пустым")
        String name,

        @NotNull(message = "id пользователя не может быть пустым")
        Long userId
) {
}
