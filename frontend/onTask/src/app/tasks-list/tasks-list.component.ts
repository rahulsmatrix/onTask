import { DatePipe, NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import { TaskDataService } from '../service/data/task-data.service';
import { Task } from '../task';
import { response } from 'express';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';
import { Router } from '@angular/router';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';

@Component({
  selector: 'app-tasks-list',
  imports: [
      NgFor,
      DatePipe,
      NgIf
      ],
  templateUrl: './tasks-list.component.html',
  styleUrl: './tasks-list.component.css'
})
export class TasksListComponent {

  tasks!: Task[];
  userId!: number | null;
  message!: string;
  task!: Task; 

  constructor(
    // private hardcodedAuthenticationService: HardcodedAuthenticationService,
    private basicAuthenticationService: BasicAuthenticationService,
    private taskService: TaskDataService,
    private router: Router
  ) {}

  ngOnInit() {
    if (this.basicAuthenticationService.isUserLoggedIn()) {
      this.userId = this.basicAuthenticationService.getUserId();
    }
    this.refreshTasks(this.userId);
  }

  refreshTasks(id: number | null) {
    id = this.userId;
    this.taskService.retrieveAllTasks(id).subscribe(
      response => {
        console.log(response);
        this.tasks = response;
      }
    )
  }

  deleteTask(id: number) {
    console.log(`Delete task ${id}`);
    this.taskService.deleteTask(this.userId, id).subscribe(
      response => {
        console.log(response);
        this.message = "Task Successfully Deleted.";
        this.refreshTasks(this.userId);
      }
    )
  }

  updateTask(id: number) {
    console.log(`Updated task ${id}`);
    this.router.navigate(['tasks', id]);
  }

  addTask() {
    this.router.navigate(['task']);
  }

}
