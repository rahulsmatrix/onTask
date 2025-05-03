import { Component, OnInit } from '@angular/core';
import { HardcodedAuthenticationService } from '../service/hardcoded-authentication.service';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent implements OnInit {

  constructor(
    // private hardcodedAuthenticationService: HardcodedAuthenticationService,
    private basicAuthenticationService : BasicAuthenticationService
    ) {

  }

  ngOnInit(): void {
      this.basicAuthenticationService.logout();
  }

}
