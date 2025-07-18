import {Component} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {PatientAnalyticsGenderComponent} from '../patient-analytics-gender/patient-analytics-gender.component';
import {PatientAnalyticsAgeGroupComponent} from '../patient-analytics-age-group/patient-analytics-age-group.component';

@Component({
  selector: 'app-patient-analytics',
  imports: [
    MatTab,
    MatTabGroup,
    PatientAnalyticsGenderComponent,
    PatientAnalyticsAgeGroupComponent
  ],
  templateUrl: './patient-analytics.component.html',
  styleUrl: './patient-analytics.component.css'
})
export class PatientAnalyticsComponent {

}
