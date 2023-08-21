package com.artyomhack.todo.controller;

import com.artyomhack.todo.model.task.TaskData;
import com.artyomhack.todo.model.task.TaskRequest;
import com.artyomhack.todo.service.task.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/create")
    public String showCreateForm() {
        return "task/taskForm";
    }

    @PostMapping("/create")
    public ModelAndView createForm(TaskRequest taskRequest) {
        var task = taskService.create(taskRequest);
        return new ModelAndView("redirect:/task/" + task.getId());
    }

    @GetMapping("/edit/{id:[0-9]}")
    public String showEditForm(@PathVariable(name = "id") String id, Model model) {
        var task = taskService.fetchById(Long.valueOf(id));
        model.addAttribute("task", task);
        return "task/taskEdit";
    }

    @PostMapping("/update/{id:[0-9]}")
    public String updateTask(@PathVariable(name = "id") String id, @ModelAttribute("task") TaskRequest taskRequest) {
        var task = taskService.update(Long.valueOf(id), taskRequest);
        return "redirect:/task/" + task.getId();
    }

    @GetMapping("/list")
    public String getTasks(Model model) {
        var tasks = taskService.fetchAll();
        model.addAttribute("tasks", tasks);
        return "task/taskList";
    }

    @GetMapping("/{id:[0-9]}")
    public ModelAndView getTask(@PathVariable(name = "id") String id) {
        var task = taskService.fetchById(Long.valueOf(id));
        var model = getForm(task);
        model.setViewName("task/taskInfo");
        return model;
    }

    @GetMapping("/delete/{id:[0-9]}")
    public String deleteTask(@PathVariable(name = "id") String id) {
        var isDeleted = taskService.deleteById(Long.valueOf(id));
        return isDeleted ? "redirect:/task/list" : "errorPage";
    }

    private ModelAndView getForm(TaskData data) {
        var model = new ModelAndView();
        model.getModelMap().addAttribute("id", data.getId());
        model.getModelMap().addAttribute("title", data.getTitle());
        model.getModelMap().addAttribute("description", data.getDescription());
        return model;
    }
}
