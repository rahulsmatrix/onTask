import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, numberAttribute } from '@angular/core';
import { User } from '../../user';
import { API_URL } from '../../app.constants';
import { BasicAuthenticationService } from '../basic-authentication-service.service';

export class WelcomeMessage {
  constructor(public message: string) {

  }
}

@Injectable({
  providedIn: 'root'
})
export class WelcomeDataService {

  // user = new User
  // id!: number;
  // userId!: number;

  constructor(
    private http:HttpClient,
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

  executeWelcomeUserService(userId: number | null) {
    let headers = this.createAuthHeaders();
    return this.http.get<WelcomeMessage>(`${API_URL}/welcome/user/${userId}`, 
    {headers}
    );
  }

}
