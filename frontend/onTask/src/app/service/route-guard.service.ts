import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, GuardResult, MaybeAsync, Router, RouterStateSnapshot } from '@angular/router';
import { HardcodedAuthenticationService } from './hardcoded-authentication.service';
import { BasicAuthenticationService } from './basic-authentication-service.service';

@Injectable({
  providedIn: 'root'
})
export class RouteGuardService implements CanActivate {

  constructor(
    // private hardcodedAuthenticationService: HardcodedAuthenticationService,
    private basicAuthenticationService: BasicAuthenticationService,
    private router: Router
    ) { 
      
   }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
   
    if(this.basicAuthenticationService.isUserLoggedIn()) {
      return true;
    }

    this.router.navigate(['login']);

    return false;
    
  }
  
}
