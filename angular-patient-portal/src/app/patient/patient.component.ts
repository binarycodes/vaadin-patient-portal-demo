import {Component} from '@angular/core';
import {Patient} from './patient';
import {PatientService} from './patient.service';
import {RouterLink} from '@angular/router';
import {MatTableModule} from '@angular/material/table';

@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  imports: [RouterLink, MatTableModule],
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  patients: Patient[] = [];
  displayedColumns: string[] = ['name', 'id', 'medicalRecord', 'doctor', 'age', 'lastVisit'];

  constructor(private patientService: PatientService) {
  }

  ngOnInit() {
    this.getPersons();
  }

  getPersons(): void {
    this.patientService.findAll().subscribe(patients => this.patients = patients);
  }
}
