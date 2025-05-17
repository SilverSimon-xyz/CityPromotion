import { Routes } from '@angular/router';

import { UsersComponent } from './core/components/users/users.component';
import { HomeComponent } from './core/components/home/home.component';
import { LoginComponent } from './core/components/login/login.component';
import { authGuard } from './auth.guard/auth.guard';
import { DashboardComponent } from './core/dashboard/dashboard.component';
import { PoiComponent } from './core/components/poi/poi.component';
import { ContestComponent } from './core/components/contest/contest.component';

export const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: '/home'},
    {path: 'home',component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'users', component: UsersComponent},
    {path: 'poi', component: PoiComponent},
    {path: 'contest', component: ContestComponent},
    {path: 'dashboard', component: DashboardComponent, canActivate:[authGuard]},
    {path: '**', redirectTo:'home'},
    
];

export class AppRouteModule {
}