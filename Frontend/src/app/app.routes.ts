import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { authGuard } from './auth.guard';


export const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: 'home'},
    {path: 'login', component: LoginComponent},
    {path: 'home',component: HomeComponent, canActivate:[authGuard]},
    {path: '**', redirectTo:'login'},
    
];

export class AppRouteModule {
}