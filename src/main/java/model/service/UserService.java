package model.service;

import lombok.Getter;
import model.entity.User;
import model.repository.UserRepository;

import java.util.List;

public class UserService implements Service<User> {
    @Getter
    private static UserService service = new UserService();

    private UserService() {
    }

    @Override
    public void save(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            userRepository.save(user);
        }

    }

    @Override
    public void edit(User user) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(user.getId()) != null) {
                userRepository.edit(user);
            } else {
                throw new Exception("User not found");
            }
        }

    }

    @Override
    public void delete(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            if (userRepository.findById(id) != null) {
                userRepository.delete(id);
            } else {
                throw new Exception("User not found");
            }

        }

    }

    @Override
    public List<User> findAll() throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findAll();
        }

    }

    @Override
    public User findById(int id) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findById(id);
        }
    }

    public static List<User> findByNameAndFamily(String name, String family) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findByNameAndFamily(name, family);
        }
    }

    public static User findByUsername(String username) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findByUsername(username);
        }
    }

    public static User findByUsernameAndPassword(String username, String password) throws Exception {
        try (UserRepository userRepository = new UserRepository()) {
            return userRepository.findByUsernameAndPassword(username, password);
        }
    }
}
