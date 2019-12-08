package org.auk.todo.controller;

import org.auk.todo.model.Label;
import org.auk.todo.model.Todo;
import org.auk.todo.model.User;
import org.auk.todo.service.LabelService;
import org.auk.todo.service.TodoService;
import org.auk.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    LabelService labelService;

    @Autowired
    private UserService userService;

    public TodoController(TodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping(value={"/users/todos",""})
    public String getIndex(Model model){
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todoList",todos);
        return "index";
    }

    @GetMapping("/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username,User user){
         User us =  userService.findByUsername(username);
        return todoService.findAllByUser(us);

    }

    @GetMapping(value ={"/users/todos/view/{id}"})
    public String viewTodo (@PathVariable Long id, Model model){
        model.addAttribute("todo",todoService.findById(id));
        return "view";
    }

    //Filtering by label
    @GetMapping(value = {"/users/todos/label/{slug}"})
    public String viewTodoByLabel(@PathVariable Model model, Label label,String slug){
         List<Label> labels = labelService.findBySlug(slug);
        List<Todo> todos = todoService.findAllByLabel(label);
        model.addAttribute("todo",todos);
        return "index"; //todo
    }

    //Filtering by priority
    @GetMapping(value = {"/users/todos/{priority}"})
    public String viewByPriority(@PathVariable Long id, Model model, Todo todo,int priority){
        List<Todo> todos = todoService.findByPriority(priority);
        model.addAttribute("todo",todos);
        return "index"; //todo
    }

    @GetMapping(value = {"/users/todos/update/{id}"})
    public String update(@PathVariable Long id,Model model){
        model.addAttribute("todo",todoService.findById(id));
        return "edit";
    }

    @PostMapping(value = {"/users/todos/update/{id}"})
    public String getEditPage(@PathVariable Long id,Todo todo, Model model){
        Todo original = todoService.findById(id);
        original.setTitle(todo.getTitle());
        original.setStatus(todo.getStatus());
        original.setDescription(todo.getDescription());
        original.setStatus(todo.getStatus());
        todoService.save(original);
        model.addAttribute("todo",todoService.findById(id));
        model.addAttribute("successMessage","Todo successfully updated");
        return "result";
    }

    @GetMapping(value={"/users/todos/delete/{id}"})
    public String getDeletePage(@PathVariable Long id, Model model){
        model.addAttribute("todo", todoService.findById(id));
        return "delete";
    }
    @PostMapping(value = "/users/todos/delete/{id}")
    public String deletePostWithId(@PathVariable Long id, Model model) {
        todoService.deleteById(id);
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todoList", todos);
        return "index";
    }

    @GetMapping(value = "/users/todos/new")
    public String getTaskForm(Model model) {
        User user = userService.getLoggedInUser();
        model.addAttribute("todo", new Todo());
        return "newTask";
    }

    @PostMapping(value = "/users/todos/new")
    public String createTask(@Valid Todo todo, Model model) {
        //todo.setUser(user);
        todoService.save(todo);
        model.addAttribute("successMessage", "Todo successfully created!");
        model.addAttribute("todo", todo);
        return "result";
    }



}
