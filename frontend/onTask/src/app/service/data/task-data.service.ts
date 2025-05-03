import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Task } from '../../task';
import { API_URL } from '../../app.constants';
import { BasicAuthenticationService } from '../basic-authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class TaskDataService {

  constructor(
    private http: HttpClient,
    private basicAuthenticationService: BasicAuthenticationService
  ) { }

  private createAuthHeaders(): HttpHeaders {
    let jwtToken = this.basicAuthenticationService.getAuthenticatedToken();
    let id = this.basicAuthenticationService.getUserId();
    let headers = new HttpHeaders();
  
    if(jwtToken && id) {
      headers = new HttpHeaders({
        Authorization: `Bearer ${jwtToken}`
      })
    }

    return headers;
  }

  retrieveAllTasks(userId: number | null) {
      // id = this.user.id
      // console.log("Execute welcome user service.")
      let headers = this.createAuthHeaders();
      return this.http.get<Task[]>(`${API_URL}/user/${userId}/tasks`, {headers});
    }

    deleteTask(userId: number | null, id: number) {
      let headers = this.createAuthHeaders();
      return this.http.delete(`${API_URL}/user/${userId}/tasks/${id}`, {headers});
    }

    retrieveTask(userId: number | null, id: number) {
      let headers = this.createAuthHeaders();
      return this.http.get<Task>(`${API_URL}/user/${userId}/tasks/${id}`, {headers});
    }

    updateTask(userId: number | null, id: number, task: Task) {
      let headers = this.createAuthHeaders();
      return this.http.put(`${API_URL}/user/${userId}/tasks/${id}`, task, {headers});
    }

    createTask(userId: number | null, task: Task) {
      let headers = this.createAuthHeaders();
      return this.http.post(`${API_URL}/user/${userId}/tasks`, task, {headers});
    }

}
