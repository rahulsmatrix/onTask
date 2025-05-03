import { Component, OnInit } from '@angular/core';
import { BasicAuthenticationService } from '../service/basic-authentication-service.service';

@Component({
  selector: 'app-delete',
  imports: [],
  templateUrl: './delete.component.html',
  styleUrl: './delete.component.css'
})
export class DeleteComponent implements OnInit {

  constructor(
    private basicAuthenticationService: BasicAuthenticationService
  ) {}

  ngOnInit(): void {
      this.basicAuthenticationService.logout();
  }

}
