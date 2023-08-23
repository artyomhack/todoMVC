package com.artyomhack.todo.controller;

import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.model.user.UserData;
import com.artyomhack.todo.model.user.UserRequest;
import com.artyomhack.todo.service.task.TaskService;
import com.artyomhack.todo.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TaskService taskService;

    public UserController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "user/userForm";
    }

    @PostMapping("/create")
    public ModelAndView createUser(UserRequest request) {
        var user = userService.create(request);
        return new ModelAndView("redirect:/user/" + user.getId());
    }

    @GetMapping("/{id:[0-9]}")
    public ModelAndView getUser(@PathVariable("id") String id) {
        var user = userService.fetchById(Long.valueOf(id));
        var model = getForm(user);
        model.setViewName("user/userInfo");
        return model;
    }

    @GetMapping("/list")
    public String getUsers(Model model) {
        var users = userService.fetchAll();
        model.addAttribute("users", users);
        return "user/userList";
    }

    @GetMapping("/task/create/{id:[0-9]}")
    public String showAddTaskUserById(@PathVariable(name = "id") String id, Model model) {
        var user = userService.fetchById(Long.valueOf(id));
        model.addAttribute("user", user);
        return "/task/taskForm";
    }

    @PostMapping("/task/create/{id:[0-9]}")
    public String addTaskUserById(@PathVariable("id") String id, @ModelAttribute(name = "task") TaskRequest request) {
        request.setId(null);
        var user = userService.addTaskByUserId(Long.valueOf(id), request);
        return "redirect:/user/" + user.getId();
    }

    @PostMapping("/task/delete/{id:[0-9]}")
    public String removeSelectTask(@RequestParam("selectTask")List<String> taskIds,
                                   @PathVariable(name = "id") String userId) {
        taskIds.forEach(taskId-> userService.deleteTaskByIdFromUserById(
                Long.valueOf(taskId),
                Long.valueOf(userId))
        );
        return "redirect:/user/" + userId;
    }

    public ModelAndView getForm(UserData user) {
        var model = new ModelAndView();
        if (!user.getTasks().isEmpty())
            model.getModelMap().addAttribute("tasks", user.getTasks());
        model.getModelMap().addAttribute("userId", user.getId());
        model.getModelMap().addAttribute("fullName", user.getFullName());
        model.getModelMap().addAttribute("email", user.getEmail());
        return model;
    }
}
