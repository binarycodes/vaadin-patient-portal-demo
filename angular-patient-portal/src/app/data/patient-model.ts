import {FormControl} from '@angular/forms';
import {Gender} from './gender';

export interface PatientFormModel {
  firstName: FormControl<string>;
  lastName: FormControl<string>;
  gender: FormControl<Gender>;
  dateOfBirth: FormControl<Date>;
  ssn: FormControl<string>;
  doctor: FormControl<string>;
  medicalRecordNumber: FormControl<number>;
}
