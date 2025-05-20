import { Routes } from '@angular/router';

import { UsersComponent } from './core/components/users/users.component';
import { HomeComponent } from './core/components/home/home.component';
import { LoginComponent } from './core/components/login/login.component';
import { authGuard } from './auth.guard/auth.guard';
import { DashboardComponent } from './core/components/dashboard/dashboard.component';
import { PoiComponent } from './core/components/poi/poi.component';
import { ContestComponent } from './core/components/contest/contest.component';
import { MultimediaContentComponent } from './core/components/multimedia.content/multimedia.content.component';
import { roleGuard } from './role.guard/role.guard';
import { AdminDashboardComponent } from './core/components/admin.dashboard/admin.dashboard.component';

export const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: '/home'},
    {path: 'home',component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'dashboard', component: DashboardComponent, canActivate:[authGuard]},
    {path: 'dashboard/admin', component: AdminDashboardComponent, canActivate:[roleGuard], data: {role: 'ADMIN'}},
    {path: 'users', component: UsersComponent},
    {path: 'users/:id', component: UsersComponent},
    {path: 'poi', component: PoiComponent},
    {path: 'contest', component: ContestComponent},
    {path: 'contents', component: MultimediaContentComponent},
    {path: '**', redirectTo:'home'},
    
];

export class AppRouteModule {
}