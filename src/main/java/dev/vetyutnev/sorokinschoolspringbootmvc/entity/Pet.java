package dev.vetyutnev.sorokinschoolspringbootmvc.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Pet {

    Long id;

    @NotBlank(message = "имя не может быть пустым")
    private String name;

    @NotNull(message = "id владельца не может быть пустым")
    private Long userId;

    public Pet() {
    }

    public Pet(Long id, String name, Long userId) {
        this.id = id;
        this.name = name;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
