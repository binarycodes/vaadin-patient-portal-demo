import {Component, ViewChild} from '@angular/core';
import {RouterLink} from '@angular/router';
import {MatTableModule} from '@angular/material/table';
import {Patient} from '../../data/patient';
import {PatientService} from '../../service/patient.service';
import {MatDrawer, MatDrawerContainer} from '@angular/material/sidenav';
import {PatientInfoComponent} from '../patient-info/patient-info.component';


@Component({
  selector: 'app-patient',
  templateUrl: './patient.component.html',
  imports: [RouterLink, MatTableModule, MatDrawerContainer, MatDrawer, PatientInfoComponent],
  styleUrls: ['./patient.component.css']
})
export class PatientComponent {
  @ViewChild('drawer') drawer!: MatDrawer;

  patients: Patient[] = [];
  displayedColumns: string[] = ['name', 'id', 'medicalRecord', 'doctor', 'age', 'lastVisit'];
  selectedPatient: Patient | undefined;

  constructor(private patientService: PatientService) {
  }

  ngOnInit() {
    this.getPatients();
  }

  getPatients(): void {
    this.patientService.findAll().subscribe(patients => this.patients = patients);
  }

  onRowDoubleClick(patient: Patient) {
    this.selectedPatient = patient;
    this.drawer.open();
  }
}
