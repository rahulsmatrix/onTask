package com.pvsnpexchange.ontask_restful_webservice.repository;

import com.pvsnpexchange.ontask_restful_webservice.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("taskRepository")
public interface TaskRepository extends JpaRepository<Task, Integer> {

    public List<Task> findAllByUsersId(int userId);
    public void deleteAllByUsersId(int theId);

    //    public void deleteByUsers_Id(int theId);
}
