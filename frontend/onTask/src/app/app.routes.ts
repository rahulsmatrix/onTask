import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { NgModule } from '@angular/core';
import { ErrorComponent } from './error/error.component';
import { TasksListComponent } from './tasks-list/tasks-list.component';
import { LogoutComponent } from './logout/logout.component';
import { RouteGuardService } from './service/route-guard.service';
import { TaskComponent } from './task/task.component';
import { RegistrationComponent } from './registration/registration.component';
import { DeleteComponent } from './delete/delete.component';

export const routes: Routes = [
    { path: '', component: LoginComponent},
    { path: 'login', component: LoginComponent},
    { path: 'registration', component: RegistrationComponent},
    { path: 'welcome/:id', component: WelcomeComponent, canActivate: [RouteGuardService]},
    { path: 'tasks', component: TasksListComponent, canActivate: [RouteGuardService]},
    { path: 'logout', component: LogoutComponent, canActivate: [RouteGuardService]},
    { path: 'tasks/:id', component: TaskComponent, canActivate: [RouteGuardService]},
    { path: 'task', component: TaskComponent, canActivate: [RouteGuardService]},
    { path: 'accountdelete', component: DeleteComponent, canActivate: [RouteGuardService]},
    { path: '**', component: ErrorComponent}
];


// @NgModule({
//     imports: [RouterModule.forRoot(routes)],
//     exports: [RouterModule] 
// })

// export class AppRoutingModule {}
