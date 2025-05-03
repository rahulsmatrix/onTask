package com.pvsnpexchange.ontask_restful_webservice.service;

import com.pvsnpexchange.ontask_restful_webservice.entity.Task;
import com.pvsnpexchange.ontask_restful_webservice.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {

    public List<Task> findAllByUsersId(int userId);

    public Task findById(int taskId);

    public Task save(Task theTask);

    public void deleteById(int taskId);

    public void deleteAllByUsersId(int theId);

//    public void deleteByUsers_Id(int theId);

}
