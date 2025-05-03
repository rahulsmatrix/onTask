import { Component, OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { Router, RouterLink, RouterModule } from '@angular/router';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';
import { NgIf } from '@angular/common';
import { LoginComponent } from '../login/login.component';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';
import { RegistrationService } from '../service/registration.service';

@Component({
  selector: 'app-menu',
  imports: [RouterLink,
            NgIf,
            RouterModule
            ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.css'
})
export class MenuComponent implements OnInit {

  userId!: number | null;
  message = '';
  unableToDelete = false;
  // errorMessage = 'Error occurred while deleting.';

  // isUserLoggedIn: boolean = false;

  constructor( 
    // protected hardcodedAuthenticationService: HardcodedAuthenticationService,
    private router: Router,
    protected basicAuthenticationService: BasicAuthenticationService,
    private registrationService: RegistrationService
    ) { }

  ngOnInit(): void {
    // this.isUserLoggedIn = this.hardcodedAuthenticationService.isUserLoggedIn();   
    if (this.basicAuthenticationService.isUserLoggedIn()) {
      this.userId = this.basicAuthenticationService.getUserId();
    }
  }

  deleteAccount() {
    if (this.basicAuthenticationService.isUserLoggedIn()) {
      this.userId = this.basicAuthenticationService.getUserId();
    }
    this.registrationService.deleteUser(this.userId).subscribe(
      response => {
        if(response) {
          console.log("Account deleted.", response);
          this.router.navigate(['accountdelete']);
        }
        else {
          console.log("Error during deleting user.");
          this.message = "Failed to delete user. Please try again.";
          this.unableToDelete = true;
        }
      },
      error => {
        console.log("exception occurred in deleting", error);
        this.message = error.error;
      }
    )
  }

}
