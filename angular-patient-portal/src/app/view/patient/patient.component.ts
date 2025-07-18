import {Component} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import {Patient} from '../../data/patient';
import {PatientService} from '../../service/patient.service';


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
    this.getPatients();
  }

  getPatients(): void {
    this.patientService.findAll().subscribe(patients => this.patients = patients);
  }
}
