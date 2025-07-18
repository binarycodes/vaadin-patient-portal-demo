import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable} from "rxjs";
import {environment} from '../../environments/environment';
import {Patient} from '../data/patient';

@Injectable({
  providedIn: 'root',
})
export class PatientService {
  private baseUrl = environment.apiUrl;

  constructor(private http: HttpClient) {
  }

  findAll(): Observable<Patient[]> {
    return this.http.get<Patient[]>(`${this.baseUrl}/patient/all`)
  }

  count(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/patient/count`)
  }

  countByAgeGroup(): Observable<Map<string, number>> {
    return this.http
      .get<{ [ageGroup: string]: number }>(`${this.baseUrl}/patient/count/groupByAge`)
      .pipe(
        map(obj => new Map<string, number>(Object.entries(obj)))
      );
  }

  countByGender(): Observable<Map<string, number>> {
    return this.http
      .get<{ [gender: string]: number }>(`${this.baseUrl}/patient/count/groupByGender`)
      .pipe(
        map(obj => new Map<string, number>(Object.entries(obj)))
      );
  }

  save(person: Patient): Observable<any> {
    return this.http.post(`${this.baseUrl}/patient/save`, person);
  }
}
