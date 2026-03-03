package dev.vetyutnev.sorokinschoolspringbootmvc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank(message = "имя не должно быть пустым")
         String name,

        @NotBlank(message = "почта не должна быть пустой")
        @Email(message = "некорректный формат email")
        String email,

        @Min(value = 0, message = "возраст не может быть отрицательным")
        Integer age
) {
}
