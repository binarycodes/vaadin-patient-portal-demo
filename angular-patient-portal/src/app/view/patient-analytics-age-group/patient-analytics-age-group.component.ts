import {Component} from '@angular/core';
import {BarChartModule} from '@swimlane/ngx-charts';
import {PatientService} from '../../service/patient.service';

type ChartData = {
  name: string;
  value: number;
};

@Component({
  selector: 'app-patient-analytics-age-group',
  imports: [
    BarChartModule
  ],
  templateUrl: './patient-analytics-age-group.component.html',
  styleUrl: './patient-analytics-age-group.component.css'
})
export class PatientAnalyticsAgeGroupComponent {
  data: ChartData[] = [];
  showXAxis = true;
  showYAxis = true;
  gradient = false;
  showLegend = true;
  showXAxisLabel = true;
  xAxisLabel = 'Age Group';
  showYAxisLabel = true;
  yAxisLabel = 'Number of patients';

  constructor(private patientService: PatientService) {
  }

  ngOnInit() {
    this.getChartData();
  }

  getChartData(): void {
    this.patientService.countByAgeGroup().subscribe(map =>
      this.data = Array.from(map, ([name, value]) => ({name, value}))
    );
  }
}
