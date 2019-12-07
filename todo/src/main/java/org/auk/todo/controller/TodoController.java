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

    @GetMapping(value={"/todos",""})
    public String getIndex(Model model){
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todoList",todos);
        return "index";
    }

    @GetMapping(value ={"/todos/view/{id}"})
    public String viewTodo (@PathVariable Long id, Model model){
        model.addAttribute("todo",todoService.findById(id));
        return "view";
    }

    //Filtering by label
    @GetMapping(value = {"todos/label/{slug}"})
    public String viewTodoByLabel(@PathVariable Model model, Label label,String slug){
         List<Label> labels = labelService.findBySlug(slug);
        List<Todo> todos = todoService.findAllByLabel(label);
        model.addAttribute("todo",todos);
        return "label"; //todo
    }

    //Filtering by priority
    @GetMapping(value = {"todos/priority"})
    public String viewByPriority(@PathVariable Long id, Model model, Todo todo){
        List<Todo> todos = todoService.findByPriority(todo.getPriority());
        model.addAttribute("todo",todos);
        return "priority"; //todo
    }

    @GetMapping(value = {"/todos/update/{id}"})
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

    @GetMapping(value={"todos/delete/{id}"})
    public String getDeletePage(@PathVariable Long id, Model model){
        model.addAttribute("todo", todoService.findById(id));
        return "delete";
    }
    @DeleteMapping("/todos/delete/{id}")
    //@RequestMapping(value = "/blog_posts/{id}", method = RequestMethod.DELETE)
    public String deletePostWithId(@PathVariable Long id, Model model) {
        todoService.deleteById(id);
        List<Todo> todos = todoService.findAll();
        model.addAttribute("todoList", todos);
        return "index";
    }

    @GetMapping(value = "/todos/new")
    public String getTaskForm(Model model) {
        User user = userService.getLoggedInUser();
        model.addAttribute("todo", new Todo());
        return "newTask";
    }

    @PostMapping(value = "/todos/new")
    public String createTask(@Valid Todo todo, Model model) {
        //todo.setUser(user);
        todoService.save(todo);
        model.addAttribute("successMessage", "Todo successfully created!");
        model.addAttribute("todo", todo);
        return "result";
    }



}
