package repo;

import models.User;

import java.util.List;

public interface UserRepo {
    void add(User article);
    User findById(int id);
    List<User> getAll();
    void deleteById(int id);
    void clearAll();
}
