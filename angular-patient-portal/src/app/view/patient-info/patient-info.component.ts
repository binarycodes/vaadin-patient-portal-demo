import {Component, Input} from '@angular/core';
import {Patient} from '../../data/patient';
import {MatFormField, MatInput, MatLabel, MatSuffix} from '@angular/material/input';
import {FormGroup, NonNullableFormBuilder, ReactiveFormsModule, Validators} from '@angular/forms';
import {MatRadioButton, MatRadioGroup} from '@angular/material/radio';
import {MatDatepicker, MatDatepickerInput, MatDatepickerToggle} from '@angular/material/datepicker';
import {MatIconModule} from '@angular/material/icon';
import {MatButton, MatIconButton} from '@angular/material/button';
import {PatientFormModel} from '../../data/patient-model';
import {Gender} from '../../data/gender';
import {PatientService} from '../../service/patient.service';
import {MatDrawer} from '@angular/material/sidenav';

@Component({
  selector: 'app-patient-info',
  templateUrl: './patient-info.component.html',
  imports: [
    MatFormField,
    MatLabel,
    MatInput,
    ReactiveFormsModule,
    MatRadioGroup,
    MatRadioButton,
    MatDatepickerInput,
    MatDatepickerToggle,
    MatDatepicker,
    MatIconModule,
    MatSuffix,
    MatButton,
    MatIconButton
  ],
  styleUrl: './patient-info.component.css'
})
export class PatientInfoComponent {
  @Input() patient!: Patient;
  @Input() drawer!: MatDrawer;

  form!: FormGroup<PatientFormModel>;

  constructor(private fb: NonNullableFormBuilder,
              private patientService: PatientService) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      firstName: this.fb.control<string>(this.patient.firstName, Validators.required),
      lastName: this.fb.control<string>(this.patient.lastName, Validators.required),
      gender: this.fb.control<Gender>(this.patient.gender, Validators.required),
      dateOfBirth: this.fb.control<Date>(this.patient.dateOfBirth, Validators.required),
      ssn: this.fb.control<string>(this.patient.ssn, Validators.required),
      doctor: this.fb.control<string>(this.patient.doctor, Validators.required),
      medicalRecordNumber: this.fb.control<number>(this.patient.medicalRecordNumber, Validators.required)
    });
  }

  save() {
    if (this.form.valid) {
      const formData: Patient = this.form.getRawValue() as Patient;

      this.patientService.save(formData).subscribe(value => {
        this.drawer.close();
      });
    } else {
      this.form.markAllAsTouched(); // trigger validation display
    }
  }
}
