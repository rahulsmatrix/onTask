package com.pvsnpexchange.ontask_restful_webservice.service;

import com.pvsnpexchange.ontask_restful_webservice.entity.Task;
import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import com.pvsnpexchange.ontask_restful_webservice.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("taskService")
public class TaskServiceImplementation implements TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImplementation(@Qualifier("taskRepository") TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public List<Task> findAllByUsersId(int userId) {
        return taskRepository.findAllByUsersId(userId);
    }

    @Override
    public Task findById(int taskId) {
        Optional<Task> result = taskRepository.findById(taskId);

        Task theTask = null;

        if (result.isPresent()) {
            theTask =result.get();
        }
        else {
            // We did not find the employee
            throw new RuntimeException("Did not find task with id - " + taskId);
        }

        return theTask;
    }

    @Override
    public Task save(Task theTask) {
        Task savedTask = taskRepository.save(theTask);
        return savedTask;
    }

    @Override
    public void deleteById(int taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public void deleteAllByUsersId(int theId) {
        taskRepository.deleteAllByUsersId(theId);
    }



    //    @Override
//    public void deleteByUsers_Id(int theId) {
//        taskRepository.deleteByUsers_Id(theId);
//    }


}
