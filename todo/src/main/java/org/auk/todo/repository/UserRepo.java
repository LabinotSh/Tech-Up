package org.auk.todo.repository;

import org.auk.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {


    public User findByUsername(String username);
    public User save(User user);


}