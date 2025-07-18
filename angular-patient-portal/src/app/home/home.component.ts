import {Component} from '@angular/core';
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {PatientComponent} from "../patient/patient.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
  imports: [MatTab, MatTabGroup, PatientComponent]
})
export class HomeComponent {

}
