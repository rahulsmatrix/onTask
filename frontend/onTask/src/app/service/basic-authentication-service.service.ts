import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { WelcomeMessage } from './data/welcome-data.service';
import { User } from '../user';
import { map } from 'rxjs/operators';
import { API_URL } from '../app.constants';

@Injectable({
  providedIn: 'root'
})
export class BasicAuthenticationService {

  user!: User;
  private userId!: number | null;
  private userKey = 'authenticatedUser';

  constructor(
    private http: HttpClient,
  ) { }

    executeJWTAuthenticationService(userId: number, email: string, jwtToken: string) {

      // console.log(jwtToken);
      // Ensure you handle the token correctly
      if (jwtToken) {
        sessionStorage.setItem(this.userKey, JSON.stringify({email: email, id: userId}));
        // sessionStorage.setItem('token', `${data.token}`);
        sessionStorage.setItem('token', jwtToken);  // Store the JWT token
        return jwtToken;
        }
        else {
          console.error("No token found in the response");
          return null;
          }
    }

    executeBasicAuthenticationService(userId: number, email: string, password: string) {
      // id = this.user.id
      // console.log("Execute welcome user service.")

      let basicAuthHeaderString = 'Basic ' + window.btoa(email + ':' + password);
  
      let headers = new HttpHeaders({
          Authorization: basicAuthHeaderString
      })
  
      return this.http.get<BasicAuthenticationBean>(
        `${API_URL}/basicauth`, {headers}
        ).pipe(
          map(
            data => {
              sessionStorage.setItem(this.userKey, JSON.stringify({email: email, id: userId}));
              sessionStorage.setItem('token', basicAuthHeaderString);
              return data;
            }
          )
        );
    }

    getAuthenticatedUser() {
      return sessionStorage.getItem(this.userKey);
    }

    getAuthenticatedToken() {
      if(this.getAuthenticatedUser()) {
        return sessionStorage.getItem('token');
      }
      return null;
    }

  
    isUserLoggedIn(): boolean {
      // let user = sessionStorage.getItem(this.userKey);
      return sessionStorage.getItem(this.userKey) !== null;
    }
  
    // Store userId when logging in
    setUser(userId: number): void {
      // We are not storing whole user in sessionStorage but just user id.
      const storedUserId = {id: userId};
      sessionStorage.setItem(this.userKey, JSON.stringify(storedUserId));
    }
  
    // Retrieve the userId/user from sessionStorage
    getUser(): any {
      const user = sessionStorage.getItem(this.userKey);
      return user ? JSON.parse(user) : null; // Parse the stored user data if exists
    }

    getUserId(): number | null {
      // Retrieve the user object stored in sessionStorage
      const userId = sessionStorage.getItem(this.userKey);
    
      // If the user object exists, parse it and return the userId
      if (userId) {
        const parsedUser = JSON.parse(userId);
        return parsedUser.id || null; // Return the userId, or null if not available
      }

      // If no user data is found, return null
      return null;
    }
  
    logout() {
      // this.userId = undefined;  // Clear userId on logout
      sessionStorage.removeItem(this.userKey);
      sessionStorage.removeItem('token');
    }

}

export class BasicAuthenticationBean {
  constructor(public message: string) {  }
}
