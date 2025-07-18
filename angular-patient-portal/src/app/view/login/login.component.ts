import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {jwtDecode, JwtPayload} from 'jwt-decode';
import {MatFormField, MatInput, MatLabel, MatSuffix} from '@angular/material/input';
import {FormsModule} from '@angular/forms';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatIcon} from '@angular/material/icon';
import {AuthService} from '../../auth/auth.service';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [
    MatFormField,
    FormsModule,
    MatInput,
    MatButton,
    MatLabel,
    MatIconButton,
    MatIcon,
    MatSuffix
  ],
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  protected readonly environment = environment;
  hidePassword: boolean = true;
  username: string = "";
  password: string = "";
  message: string = "";

  constructor(private authService: AuthService, private router: Router, private snackBar: MatSnackBar) {
  }

  public login(): void {
    sessionStorage.removeItem("app.token");

    this.authService.login(this.username, this.password)
      .subscribe({
        next: (token) => {
          sessionStorage.setItem("app.token", token);

          const decodedToken = jwtDecode<JwtPayload>(token);
          // @ts-ignore
          sessionStorage.setItem("app.roles", decodedToken.scope);

          this.router.navigateByUrl("/patients");
        },
        error: (error) => this.snackBar.open(`Login failed: ${error.status}`, "OK")
      });
  }
}
