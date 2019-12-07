package org.auk.todo.repository;

import org.auk.todo.model.Todo;
import org.auk.todo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo,Long> {

    List<Todo> findAllByOrderByCreatedAtDesc();
    public List<Todo> findByTitleLike(String title);
    public List<Todo> findAllByUser(User user);
}
