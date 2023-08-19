package com.artyomhack.todo.controller;

import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/task")
public class TaskController {
    private final static Logger LOGGER = LoggerFactory.getLogger("TaskController.class");

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "taskForm";
    }

    @PostMapping("/create")
    public ModelAndView createForm(TaskRequest taskRequest) {
        var task = taskService.create(taskRequest);
        LOGGER.info("This is task create form: {}", task.toString());
        return new ModelAndView("redirect:/task/" + task.getId());
    }

    @GetMapping("/{id:[0-9]}")
    public ModelAndView getTask(@PathVariable(name = "id") String id) {
        var model = new ModelAndView();
        var task = taskService.fetchById(Long.valueOf(id));
        LOGGER.info("This is task getTask form: {}", task.toString());
        model.getModelMap().addAttribute("title", task.getTitle());
        model.getModelMap().addAttribute("description", task.getDescription());
        model.setViewName("taskInfo");
        return model;
    }
}
