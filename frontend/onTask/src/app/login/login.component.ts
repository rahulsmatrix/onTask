import { NgIf } from '@angular/common';
import { Component, NgModule } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';
import { RegistrationService } from '../service/registration.service';
import { User } from '../user';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';
import { response } from 'express';

@Component({
  selector: 'app-login',
  imports: [FormsModule,
            NgIf,
            RouterModule
            ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  user = new User()
  userId!: number;
  message = ''
  jwtToken!: string;
  // username = 'pvsnpexchange'
  // password = ''
  errorMessage = 'Invalid Credentials'
  invalidLogin = false 
  
  constructor(private router: Router,
              private registrationService: RegistrationService,
              private hardcodedAuthenticationService: HardcodedAuthenticationService,
              private basicAuthenticationService: BasicAuthenticationService
              ) { }


  // loginUser() {

  //   this.registrationService.loginUserFromRemote(this.user).subscribe(
  //     data => {
  //       console.log("response received");
  //       // Ensuring that the response contains a user with an id.
  //       if (data && data.id) {
  //         this.userId = data.id;

  //         // this.router.navigate(['welcome'])
  //        if(this.hardcodedAuthenticationService.authenticate(this.user.email, this.user.password)) {
  //            this.hardcodedAuthenticationService.setUser(data.id);
  //            this.router.navigate(['welcome', this.userId]);
  //            this.invalidLogin=false;
  //         }
  //       }
  //       else {
  //         this.invalidLogin=true
  //       }
  //     },
  //     error => {
  //       this.message = "Bad credentials, please enter valid email and password.";
  //       console.log("exception occured")
  //     }
  //   )

  // }

  // basicAuthLogin() {

  //   this.registrationService.loginUserFromRemote(this.user).subscribe(
  //     data => {
  //       console.log("response received");
  //       // Ensuring that the response contains a user with an id.
  //       if (data && data.id) {
  //         this.userId = data.id;

  //         // this.router.navigate(['welcome'])
  //        if(this.basicAuthenticationService.executeBasicAuthenticationService(this.userId, this.user.email, this.user.password)) {
  //            this.basicAuthenticationService.setUser(data.id);
  //            this.router.navigate(['welcome', this.userId]);
  //            this.invalidLogin=false;
  //         }
  //       }
  //       else {
  //         this.invalidLogin=true
  //       }
  //     },
  //     error => {
  //       this.message = "Bad credentials, please enter valid email and password.";
  //       console.log("exception occured")
  //     }
  //   )

  // }

  jwtAuthLogin() {

    this.registrationService.loginUserFromRemote(this.user).subscribe(
      data => {
        // Log the response/data for debugging
        // console.log("response received", data);

        console.log("response received");

        // Ensuring that the response contains a user with an id and a token.
        if (data && data.user.id && data.token) {
          
          this.userId = data.user.id;
          this.user = data.user;
          this.jwtToken = data.token;

          // Ensure JWT authentication service handles the token and user data.
          this.basicAuthenticationService.executeJWTAuthenticationService(this.userId, this.user.email, this.jwtToken);
          
          // If JWT token is returned & login is successful, store the user information and token  
          // console.log("JWT authentication successful, storing token.");

          this.basicAuthenticationService.setUser(this.userId);
          this.router.navigate(['welcome', this.userId]);
          this.invalidLogin=false;
        }
        else {
            // Error that might occur during JWT authentication
            console.log("Error during JWT authentication:");
            this.message = "Failed to authenticate with JWT. Please try again.";
            this.invalidLogin = true;
          }
      },
      
      error => {
        // Handle error from backend, like invalid credentials
        console.log("exception occurred", error);
        this.message = "Bad credentials, please enter valid email and password.";
        this.invalidLogin = true;
      }
    );

  }

  goToRegistration() {
    this.router.navigate(['/registration']);
  }

}
