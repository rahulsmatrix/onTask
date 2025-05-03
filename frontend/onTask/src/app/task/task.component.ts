import { Component, NgModule, OnInit } from '@angular/core';
import { TaskDataService } from '../service/data/task-data.service';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';
import { Task } from '../task';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule, NgModel } from '@angular/forms';
import { NgIf } from '@angular/common';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';

@Component({
  selector: 'app-task',
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css'
})
export class TaskComponent implements OnInit {

  userId!: number | null;
  id!: number;
  task!: Task;

  constructor(
    private taskService: TaskDataService,
    // private hardcodedAuthenticationService: HardcodedAuthenticationService,
    private basicAuthenticationService: BasicAuthenticationService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {

    if (this.basicAuthenticationService.isUserLoggedIn()) {
      this.userId = this.basicAuthenticationService.getUserId();
    }

    this.id = this.route.snapshot.params['id'];
    this.task = new Task(this.id, '', new Date(), false);

    if(this.id) {
      this.taskService.retrieveTask(this.userId, this.id).subscribe(
        data =>{
          if(data) {
            this.task = data;
          }
        }
      )
    }
    
  }

  saveTask() {

    if(this.id) {
      this.taskService.updateTask(this.userId, this.id, this.task).subscribe(
        data => {
          console.log(data);
          this.router.navigate(['tasks']);
        }
      )
    }
    else {
      // Create task
      this.taskService.createTask(this.userId, this.task).subscribe(
        data => {
          console.log(data);
          this.router.navigate(['tasks']);
        }
      )
    }

  }

  cancelUpdatingTask() {
    this.router.navigate(['tasks']);
  }

}
