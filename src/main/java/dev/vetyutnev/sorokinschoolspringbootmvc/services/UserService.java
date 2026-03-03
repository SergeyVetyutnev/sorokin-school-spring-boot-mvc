package dev.vetyutnev.sorokinschoolspringbootmvc.services;

import dev.vetyutnev.sorokinschoolspringbootmvc.entity.Pet;
import dev.vetyutnev.sorokinschoolspringbootmvc.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final PetService petService;
    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private Long nextId = 0L;

    public UserService(PetService petService) {
        this.petService = petService;
    }

    public User getUserById(Long id) {
        User user = users.get(id);
        if (user == null){
            throw new NoSuchElementException("пользователь с id: %s не найден"
                    .formatted(id));
        }
        return user;
    }

    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }

    public User createUser(User user) {

        if (user == null) {
            throw new IllegalArgumentException("Данные о юзере не могут быть null");
        }

        User newUser = new User(
                ++nextId,
                user.getName(),
                user.getEmail(),
                user.getAge()
        );

        users.put(nextId, newUser);

        return newUser;
    }

    public User updateUser(Long id, User user) {
        User userToUpdate = users.get(id);
        if (userToUpdate == null){
            throw new NoSuchElementException("пользователь с id: %s не найден"
                    .formatted(id));
        }

        userToUpdate.setName(user.getName());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setAge(user.getAge());

        return userToUpdate;
    }


    public void deleteUser(Long id) {
        User userToDelete = getUserById(id);

        if (!userToDelete.getPets().isEmpty()){
            List<Pet> pets = new ArrayList<>(userToDelete.getPets());

            for (Pet pet : pets){
                petService.deletePet(pet.getId());
            }
        }

        users.remove(userToDelete.getId());
    }
}
