import {Routes} from '@angular/router';
import {AuthGuard} from './auth/auth-guard.service';
import {LoginComponent} from './login/login.component';
import {HomeComponent} from './home/home.component';

export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: 'home'}
];
