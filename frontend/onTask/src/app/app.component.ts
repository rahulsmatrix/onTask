import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './login/login.component';
import { MenuComponent } from './menu/menu.component';
import { FooterComponent } from './footer/footer.component';
import { LogoutComponent } from './logout/logout.component';
import { HTTP_INTERCEPTORS, HttpClient } from '@angular/common/http';
import { RegistrationComponent } from './registration/registration.component';
import { HttpIntercepterBasicAuthService } from './service/http/http-intercepter-basic-auth.service';
import { HttpModule } from './http-module/http.module';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet,
            WelcomeComponent,
            LoginComponent,
            MenuComponent,
            FooterComponent,
            LogoutComponent,
            RegistrationComponent,
            HttpModule
            ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: HttpIntercepterBasicAuthService,
      multi: true // Tells Angular this is an interceptor to be added to the chain
    }
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'onTask';
}
