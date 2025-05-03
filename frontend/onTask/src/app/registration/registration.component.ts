import { Component, ViewChild } from '@angular/core';
import { User } from '../user';
import { FormsModule, NgForm } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-registration',
  imports: [
    FormsModule,
    NgIf,
    RouterModule
  ],
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css'
})
export class RegistrationComponent {

  user = new User();
  userId!: number;
  message = '';
  errorMessage = 'Invalid values';
  invalidUserInfo = false;

  // Access the form via ViewChild
  @ViewChild('Registerform') Registerform!: NgForm;

  constructor(
    private router: Router,
    private registrationService: RegistrationService
  ) {}

  registerUser() {
    this.registrationService.registerUser(this.user).subscribe(
      data => {
        if(data) {
          console.log("response received");
          this.message = "Registration successful";
          this.invalidUserInfo = false;

          // Reset the user object to clear the fields
          this.user = new User();

          // Reset the form
          this.Registerform.resetForm();
        }
        else {
          console.log("Error during registering user.");
          this.message = "Failed to register user. Please try again.";
          this.invalidUserInfo = true;
        }
      },

      error => {
        console.log("exception occurred", error);
        this.message = error.error;
        this.invalidUserInfo = true;
      }
    )
  }

  cancelUserRegistration() {
    this.router.navigate(['login']);
  }

}
