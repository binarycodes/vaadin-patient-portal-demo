import {Component} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {PatientComponent} from "../patient/patient.component";
import {PatientAnalyticsComponent} from '../patient-analytics/patient-analytics.component';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [MatTab, MatTabGroup, PatientComponent, PatientAnalyticsComponent]
})
export class HomeComponent {
  protected readonly environment = environment;
}
