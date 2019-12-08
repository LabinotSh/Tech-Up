package org.auk.todo.controller;

import org.auk.todo.model.Todo;
import org.auk.todo.model.User;
import org.auk.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping(value = {"/admin"})
    public String adminIndex(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("userList",users);
        return "admin";
    }
    @GetMapping(value = {"/admin/update/{id}"})
    public String getEditPage(@PathVariable Long id, User user, Model model){
        User original = userService.findById(id);
        original.setFirstName(user.getFirstName());
        original.setLastName(user.getLastName());
        original.setUsername(user.getUsername());
        original.setPassword(user.getPassword());
        userService.save(original);
        model.addAttribute("user",userService.findById(id));
        model.addAttribute("successMessage","User successfully updated");
        return "resultuser";//fix
    }

    @GetMapping(value={"admin/delete/{id}"})
    public String getDeletePage(@PathVariable Long id, Model model){
        model.addAttribute("user", userService.findById(id));
        return "userdelete";
    }
    @DeleteMapping(value = {"/admin/delete/{id}"})
    // @RequestMapping(value = "/blog_posts/{id}", method = RequestMethod.DELETE)
    public String deletePostWithId(@PathVariable Long id, Model model) {
        userService.deleteById(id);
        List<User> users =userService.findAll();
        model.addAttribute("userList", users);
        return "admin";
    }

}
