import { bootstrapApplication } from '@angular/platform-browser';
import { App } from './app/app';
import { HTTP_INTERCEPTORS, provideHttpClient, withFetch, withInterceptorsFromDi } from '@angular/common/http';
import { importProvidersFrom } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { provideRouter } from '@angular/router';
import 'zone.js';
import { routes } from './app/app.routes';
import { AuthInterceptor } from './app/interceptor/auth-interceptor';

bootstrapApplication(App, {
  providers: [
    provideHttpClient(
      withInterceptorsFromDi(),
      withFetch()
    ),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    importProvidersFrom(FormsModule),
    provideRouter(routes) //added reload option here
  ]
});
