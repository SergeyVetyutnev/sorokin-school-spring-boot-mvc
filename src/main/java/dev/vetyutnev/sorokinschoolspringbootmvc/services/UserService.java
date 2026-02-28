package dev.vetyutnev.sorokinschoolspringbootmvc.services;

import dev.vetyutnev.sorokinschoolspringbootmvc.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private Long nextId = 0L;

    public User getUserById(Long id) {
        User user = users.get(id);
        if (user == null){
            throw new NoSuchElementException("пользователь с id: %s не найден"
                    .formatted(id));
        }
        return user;
    }

    //TODO: УДОСТОВЕРИТЬСЯ, ЧТО НЕТ НЕТ ПРОБЛЕМЫ N+1
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }

    public User createUser(User user) {
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
        User deletedUser = users.remove(id);

        if (deletedUser == null) {
            throw new NoSuchElementException("Пользователь с id: %s не найден".formatted(id));
        }
    }
}
