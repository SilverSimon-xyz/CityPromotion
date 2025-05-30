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
import { ContestParticipantComponent } from './core/components/contest.participant/contest.participant.component';
import { ErrorComponent } from './core/components/error/error.component';
import { adminGuard } from './admin.guard/admin.guard';

export const routes: Routes = [
    {path: '', pathMatch: 'full', redirectTo: '/dashboard'},
    {path: 'home',component: HomeComponent},
    {path: 'login', component: LoginComponent},
    {path: 'dashboard', component: DashboardComponent, canActivate:[authGuard]},

    {path: 'users', component: UsersComponent, canActivate:[adminGuard]},
    {path: 'users/add', component: UsersComponent, canActivate:[adminGuard]},
    {path: 'users/:id', component: UsersComponent, canActivate:[adminGuard]},
    {path: 'users/:id/edit', component: UsersComponent, canActivate:[adminGuard]},

    {path: 'poi', component: PoiComponent },
    {path: 'poi/add', component: PoiComponent, canActivate:[roleGuard], data: {roles: ['CONTRIBUTOR']} },
    {path: 'poi/:id', component: PoiComponent },
    {path: 'poi/:id/edit', component: PoiComponent, canActivate:[roleGuard], data: {roles: ['ADMIN']} },

    {path: 'contents', component: ContentComponent},
    {path: 'contents/add', component: ContentComponent, canActivate:[roleGuard], data: {roles: ['CONTRIBUTOR', 'CURATOR'] } },
    {path: 'contents/:id', component: ContentComponent},
    {path: 'contents/:id/edit', component: ContentComponent, canActivate:[roleGuard], data: {roles: ['ADMIN', 'CURATOR'] } },

    {path: 'contest', component: ContestComponent},
    {path: 'contest/add', component: ContestComponent, canActivate:[roleGuard], data: {roles: ['ANIMATOR']}},
    {path: 'contest/:id', component: ContestComponent},
    {path: 'contest/:id/edit', component: ContestComponent, canActivate:[roleGuard], data: {roles: ['ADMIN', 'ANIMATOR']}},

    {path: 'participants/:id', component: ContestParticipantComponent},
    {path: 'participants/:id/sign-up', component: ContestParticipantComponent, canActivate:[roleGuard], data: {blockedRoles: ['ADMIN', 'ANIMATOR']}},
    {path: '', redirectTo:'/contest', pathMatch: 'full'},

    {path: 'error',component: ErrorComponent},
    {path: '**', redirectTo:'dashboard'},
    
];

export class AppRouteModule {
}