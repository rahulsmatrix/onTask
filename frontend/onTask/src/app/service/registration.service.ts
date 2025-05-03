import { Injectable } from '@angular/core';
import { User } from '../user';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { API_URL } from '../app.constants';
import { BasicAuthenticationService } from './basic-authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

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

  public loginUserFromRemote(user: User):Observable<any> {
    return this.http.post<any>(`${API_URL}/login/user`, user);
  }

  public registerUser(user: User):Observable<any> {
    return this.http.post<any>(`${API_URL}/registeruser`, user);
  }

  public deleteUser(userId: number | null) {
    let headers = this.createAuthHeaders();
    return this.http.delete(`${API_URL}/user/${userId}`, {headers});
  }

 }
