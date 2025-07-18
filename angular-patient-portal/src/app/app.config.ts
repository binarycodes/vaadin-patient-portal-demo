import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {AuthInterceptor} from './auth/auth.interceptor';
import {provideAnimations} from '@angular/platform-browser/animations';
import {provideNativeDateAdapter} from '@angular/material/core';

export const appConfig: ApplicationConfig = {
  providers: [
    provideNativeDateAdapter(),
    provideAnimations(),
    provideZoneChangeDetection({eventCoalescing: true}),

    // Tell HttpClient to pull in DI-registered interceptors:
    provideHttpClient(
      withInterceptorsFromDi()
    ),

    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},

    provideRouter(routes),
  ]
};
