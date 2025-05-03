import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BasicAuthenticationService } from '../basic-authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class HttpIntercepterBasicAuthService implements HttpInterceptor {

  constructor(
    private basicAuthenticationService: BasicAuthenticationService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let jwtToken = this.basicAuthenticationService.getAuthenticatedToken();
    // console.log("interceptor", jwtToken);
    let email = this.basicAuthenticationService.getAuthenticatedUser();
    let id = this.basicAuthenticationService.getUserId();

    if(jwtToken && email && id) {
      request = request.clone({
        setHeaders : {
          Authorization: `Bearer ${jwtToken}`
        }
      })
    }

    return next.handle(request);
  }
}
