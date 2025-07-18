import {Routes} from '@angular/router';
import {AuthGuard} from './auth/auth-guard.service';
import {HomeComponent} from './view/home/home.component';
import {LoginComponent} from './view/login/login.component';


export const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent, canActivate: [AuthGuard]},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo: 'home'}
];
