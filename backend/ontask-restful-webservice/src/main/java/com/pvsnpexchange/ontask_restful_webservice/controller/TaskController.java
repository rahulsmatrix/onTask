package com.pvsnpexchange.ontask_restful_webservice.controller;

import com.pvsnpexchange.ontask_restful_webservice.entity.Task;
import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/user")
public class TaskController {

//    private UserRegistrationService userRegistrationService;

    private TaskService taskService;

    @Autowired
    public TaskController(@Qualifier("taskService") TaskService taskService) {
        this.taskService = taskService;
    }

    private int getAuthenticatedUserId() {
        // Retrieve the authenticated user from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User authenticatedUser = (User) authentication.getPrincipal();
            return authenticatedUser.getId(); // Get the authenticated user's ID
        }
        throw new RuntimeException("User not authenticated");
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<List<Task>> findAllTasksByUsers_Id(@PathVariable int userId) {
        int authenticatedUserId = getAuthenticatedUserId();
        System.out.println(authenticatedUserId);

        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }
        List<Task> tasks = taskService.findAllByUsersId(userId);
        return ResponseEntity.ok(tasks);
    }

    // Add mapping for creating a Task. POST "/tasks" - add a new task

    @PostMapping("/{userId}/tasks")
    public ResponseEntity<Void> addTask(@RequestBody Task theTask, @PathVariable int userId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        // Also, just in case if someone pass an id in JSON, set id to 0
        // This is to force a save of new item, instead of update
        theTask.setId(0);
        theTask.setUsersId(userId);
        Task createdTask = taskService.save(theTask);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(createdTask.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> getTask(@PathVariable int userId, @PathVariable int taskId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        Task task = taskService.findById(taskId);
        if(task == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(task);
    }

    // Add mapping for updating an existing task. PUT /tasks

    @PutMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable int userId, @PathVariable int taskId,
                           @RequestBody Task theTask) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        // To update an existing task,we need to provide id of the existing task in JSON
        theTask.setUsersId(userId);
        taskService.save(theTask);

        return new ResponseEntity<Task>(theTask, HttpStatus.OK);
    }

    // Add mapping for Deleting a task. DELETE /tasks/{taskId}
    //@DeleteMapping("{usersId}/tasks/{taskId}") : Better naming convention for clarity.

    @DeleteMapping("/{userId}/tasks/{taskId}")
    public ResponseEntity<Void> deleteTaskById(
            @PathVariable int userId, @PathVariable int taskId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        Task tempTask = taskService.findById(taskId);
        // Throw exception if null
        if(tempTask == null) {
            // throw new RuntimeException("Task id not found - " + taskId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        taskService.deleteById(taskId);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{userId}/tasks")
    public ResponseEntity<String> deleteAllTasksByUser_Id(@PathVariable int userId) {

        int authenticatedUserId = getAuthenticatedUserId();
        if(userId != authenticatedUserId) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // Unauthorized access
        }

        List<Task> tempTask = taskService.findAllByUsersId(userId);
        // Throw exception if null
        if(tempTask == null || tempTask.isEmpty()) {
            // throw new RuntimeException("Tasks of user id : " + userId + " not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tasks not found for user id - " + userId);
        }

        taskService.deleteAllByUsersId(userId);

        return ResponseEntity.ok("Deleted all tasks for user id - " + userId);
    }


}
