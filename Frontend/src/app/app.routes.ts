import { Routes } from '@angular/router';

import { UsersComponent } from './core/components/users/users.component';
import { HomeComponent } from './core/components/home/home.component';
import { LoginComponent } from './core/components/login/login.component';
import { authGuard } from './auth.guard/auth.guard';
import { DashboardComponent } from './core/components/dashboard/dashboard.component';
import { PoiComponent } from './core/components/poi/poi.component';
import { ContestComponent } from './core/components/contest/contest.component';
import { ContentComponent } from './core/components/content/content.component';
import { roleGuard } from './role.guard/role.guard';
import { AdminDashboardComponent } from './core/components/admin.dashboard/admin.dashboard.component';

export const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: '/home'},
    {path: 'home',component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'dashboard', component: DashboardComponent, canActivate:[authGuard]},
    {path: 'dashboard/admin', component: AdminDashboardComponent, canActivate:[roleGuard], data: {role: 'ADMIN'}},

    {path: 'users', component: UsersComponent},
    {path: 'users/add', component: UsersComponent},
    {path: 'users/:id', component: UsersComponent},
    {path: 'users/:id/edit', component: UsersComponent},

    {path: 'poi', component: PoiComponent},
    {path: 'poi/add', component: PoiComponent},
    {path: 'poi/:id', component: PoiComponent},
    {path: 'poi/:id/edit', component: PoiComponent},

    {path: 'contest', component: ContestComponent},
    {path: 'contest/:id', component: ContestComponent},
    {path: 'contest/add', component: ContestComponent},
    {path: 'contest/:id/edit', component: ContestComponent},

    {path: 'contents', component: ContentComponent},
    {path: 'contents/:id', component: ContentComponent},
    {path: 'contents/add', component: ContentComponent},

    {path: '**', redirectTo:'dashboard'},
    
];

export class AppRouteModule {
}