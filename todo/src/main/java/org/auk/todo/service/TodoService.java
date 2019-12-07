package org.auk.todo.service;

import org.auk.todo.model.Label;
import org.auk.todo.model.Todo;
import org.auk.todo.model.User;
import org.auk.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        List<Todo> todos = todoRepository.findAllByOrderByCreatedAtDesc();
        return todos;
    }
     //List Todos by User
    public List<Todo> findAllByUser(User user) {
        List<Todo> todos = todoRepository.findAllByUser(user);
        return todos;
    }
    //List Todos by Label
    public List<Todo> findAllByLabel(Label label){
        List<Todo> todos = todoRepository.findAllByLabel(label);
        return todos;
    }
    //List Todos by priority
    public List<Todo> findByPriority(int priority){
        List<Todo> todos = todoRepository.findByPriority(priority);
        return todos;
    }
//
//
//
//    public List<Task> findAllByStatusOrderByCreatedAtDesc(String status){
//    	List<Task> tasks = taskRepository.findAllByStatusOrderByCreatedAtDesc(status);
//    	return tasks;
//   }

    public void save(Todo todo) {
        todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        todoRepository.deleteById(id);
    }

    public Todo findById(Long id) {
        //Long[] idArray = {id, id};
        //List <Long> idList = Arrays.asList(idArray);
        //List <Todo> todoList= (List<Todo>) todoRepository.findAllById(idList);
        //when statement in Mockito returns Optional Task instead of Task below
        Todo todo = todoRepository.findById(id).get();
        //Task task = taskRepository.findById(id).orElse(null);
        return todo;

    }
}