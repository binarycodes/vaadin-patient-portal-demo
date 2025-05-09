import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  isLoggedIn(): boolean {
    return sessionStorage.getItem("app.token") != null;
  }

  login(username: string, password: string): Observable<string> {
    const auth = btoa(`${username}:${password}`);
    const httpOptions = {
      headers: {
        Authorization: `Basic ${auth}`
      },
      responseType: 'text' as 'text',
    };
    return this.http.post(`${this.baseUrl}/rest/auth`, null, httpOptions);
  }

  logout() {
    sessionStorage.removeItem("app.token");
    sessionStorage.removeItem("app.roles");
  }

  isUserInRole(roleFromRoute: string) {
    const roles = sessionStorage.getItem("app.roles");

    if (roles!.includes(",")) {
      if (roles === roleFromRoute) {
        return true;
      }
    } else {
      const roleArray = roles!.split(",");
      for (let role of roleArray) {
        if (role === roleFromRoute) {
          return true;
        }
      }
    }
    return false;
  }
}
