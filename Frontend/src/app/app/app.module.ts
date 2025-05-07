import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AppComponent } from '../app.component';
import { provideHttpClient, withFetch } from '@angular/common/http';



@NgModule({
  declarations: [

  ],
  imports: [
    CommonModule
  ],
  providers: [
    provideHttpClient(withFetch())
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule { }
