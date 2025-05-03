import { Injectable } from '@angular/core';
import { User } from '../user';

@Injectable({
  providedIn: 'root'
})
export class HardcodedAuthenticationService {

  user!: User;
  private userId!: number | null;
  private userKey = 'authenticatedUser';

  constructor() { }

  authenticate(email: string, password: string): boolean {
    // console.log('before ' + this.isUserLoggedIn());
    if(email==="parmar@test.com" && password==='p@rm@rx123'){
      // sessionStorage.setItem('authenticatedUser', email);
      sessionStorage.setItem(this.userKey, JSON.stringify({email: email}))
      // console.log('after ' + this.isUserLoggedIn());
      return true;
    }
    return false;
  }

  isUserLoggedIn(): boolean {
    let user = sessionStorage.getItem(this.userKey);
    // return this.userId !== undefined;
    return sessionStorage.getItem(this.userKey) !== null;
  }

  // Store userId when logging in
  setUser(userId: number): void {
    // this.userId = userId;
    const user = {id: userId};
    sessionStorage.setItem(this.userKey, JSON.stringify(user));
  }

  // Retrieve the user from sessionStorage
  getUser(): any {
    const user = sessionStorage.getItem(this.userKey);
    return user ? JSON.parse(user) : null; // Parse the stored user data if exists
  }

  // Get the userId (if needed)
  getUserId(): number | null {
    const user = this.getUser();
    return user ? user.id : null; // Return the userId
  }

  logout() {
    // this.userId = undefined;  // Clear userId on logout
    sessionStorage.removeItem(this.userKey);
  }

}
