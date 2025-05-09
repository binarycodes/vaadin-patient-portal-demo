import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter} from '@angular/router';

import {routes} from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptorsFromDi} from '@angular/common/http';
import {AuthInterceptor} from './auth/auth.interceptor';

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({eventCoalescing: true}),

    // Tell HttpClient to pull in DI-registered interceptors:
    provideHttpClient(
      withInterceptorsFromDi()
    ),

    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true},

    provideRouter(routes),
  ]
};
