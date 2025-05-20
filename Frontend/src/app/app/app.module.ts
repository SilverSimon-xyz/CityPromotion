import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../app.component';
import { AuthService } from '../core/services/auth/auth.service';
import { SessionStorageService } from '../core/services/session.storage/session.storage.service';
import { AppRouteModule } from '../app.routes';
import { UsersComponent } from '../core/components/users/users.component';
import { UserService } from '../core/services/user/user.service';
import { HomeComponent } from '../core/components/home/home.component';
import { LoginComponent } from '../core/components/login/login.component';
import { DashboardComponent } from '../core/components/dashboard/dashboard.component';
import { authInterceptor } from '../core/interceptor/auth/auth.interceptor';
import { tokenInterceptor } from '../core/interceptor/token/token.interceptor';
import { PoiComponent } from '../core/components/poi/poi.component';
import { PoiService } from '../core/services/poi/poi.service';
import { ContestComponent } from '../core/components/contest/contest.component';
import { ContestService } from '../core/services/contest/contest.service';
import { MultimediaContentComponent } from '../core/components/multimedia.content/multimedia.content.component';
import { MultimediaContentService } from '../core/services/multimedia.content/multimedia.content.service';
import { MediaFileComponent } from '../core/components/media.file/media.file.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    DashboardComponent,
    UsersComponent,
    MediaFileComponent,
    PoiComponent,
    ContestComponent,
    MultimediaContentComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    CommonModule,
    ReactiveFormsModule,
    AppRouteModule
  ],
  providers: [
    AuthService, 
    SessionStorageService,
    UserService,
    PoiService,
    ContestService,
    MultimediaContentService,
    provideHttpClient(withFetch(), withInterceptors([authInterceptor, tokenInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
