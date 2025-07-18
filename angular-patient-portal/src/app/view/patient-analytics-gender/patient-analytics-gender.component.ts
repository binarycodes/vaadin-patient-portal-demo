import {Component} from '@angular/core';
import {LegendPosition, PieChartModule} from '@swimlane/ngx-charts';
import {PatientService} from '../../service/patient.service';

type ChartData = {
  name: string;
  value: number;
};

@Component({
  selector: 'app-patient-analytics-gender',
  imports: [
    PieChartModule
  ],
  templateUrl: './patient-analytics-gender.component.html',
  styleUrl: './patient-analytics-gender.component.css'
})
export class PatientAnalyticsGenderComponent {
  data: ChartData[] = [];
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = false;
  isDoughnut: boolean = true;
  legendPosition: LegendPosition = LegendPosition.Right;

  constructor(private patientService: PatientService) {
  }

  ngOnInit() {
    this.getChartData();
  }

  getChartData(): void {
    this.patientService.countByGender().subscribe(map =>
      this.data = Array.from(map, ([name, value]) => ({name, value}))
    );
  }
}
