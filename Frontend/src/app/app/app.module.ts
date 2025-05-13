import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from '../app.component';
import { authInterceptor } from '../auth.interceptor';
import { AuthService } from '../services/auth/auth.service';
import { SessionStorageService } from '../services/session.storage/session.storage.service';
import { LoginComponent } from '../login/login.component';
import { HomeComponent } from '../home/home.component';
import { AppRouteModule } from '../app.routes';
import { tokenInterceptor } from '../token.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
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
    provideHttpClient(withFetch(), withInterceptors([authInterceptor, tokenInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
