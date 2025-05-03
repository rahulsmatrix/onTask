import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { WelcomeDataService } from '../service/data/welcome-data.service';
import { User } from '../user';
import { NgIf } from '@angular/common';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';

@Component({
  selector: 'app-welcome',
  imports: [RouterLink,
            NgIf
            ],
  templateUrl: './welcome.component.html',
  styleUrl: './welcome.component.css'
})
export class WelcomeComponent implements OnInit {

  user = new User;
  // id!: number;
  userId!: number | null;

  message = 'Some Welcome Message';
  welcomeMessageFromService!: string;
  errorMessage!: string;
  name='';

  // ActivatedRoute 
  constructor(
    private route:ActivatedRoute,
    private welcomeService:WelcomeDataService,
    private basicAuthenticationService: BasicAuthenticationService
    ) {
    
  }

  ngOnInit() {

    if (this.basicAuthenticationService.isUserLoggedIn()) {
      this.userId = this.basicAuthenticationService.getUserId();
    }
    else {
          console.error('User Id not found.');
        }
    // let checkId = this.route.snapshot.params['id'];
    // console.log(checkId);

      // this.route.snapshot.params['id'];
      // this.route.paramMap.subscribe(params => {
      //   const userIdParam = params.get('id');
      //   if(userIdParam) {
      //     this.userId = +userIdParam;
      //   }
      //   else {
      //     console.error('User Id not found in route parameters');
      //   }
      // });
   }

   getWelcomeMessage() {
    // console.log("Get welcome message.")
    console.log(this.welcomeService.executeWelcomeUserService(this.userId));

    this.welcomeService.executeWelcomeUserService(this.userId).subscribe(
      data => {
        this.welcomeMessageFromService = data.message;
      },
      error => {
        console.error('Error fetching welcome message:', error);
        this.errorMessage = error.error.message;
      }
    );
   }


}
