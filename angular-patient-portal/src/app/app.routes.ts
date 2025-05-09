import {Routes} from '@angular/router';
import {PatientComponent} from './patient/patient.component';
import {AuthGuard} from './auth/auth-guard.service';
import {LoginComponent} from './login/login.component';

export const routes: Routes = [
  {path: '', redirectTo: 'patients', pathMatch: 'full'},
  {path: 'patients', component: PatientComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: 'patients'}
];
