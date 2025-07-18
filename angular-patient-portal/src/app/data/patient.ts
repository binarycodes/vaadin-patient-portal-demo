import {Gender} from './gender';

export class Patient {
  static readonly AGE_GROUP_PERIOD = 10;

  id?: number;
  avatarUrl?: string;
  name?: string;
  firstName!: string;
  lastName!: string;
  gender!: Gender;
  ssn!: string;
  doctor!: string;
  medicalRecordNumber!: number;
  dateOfBirth!: Date;
  age!: number;
  lastVisitedOn?: Date;

  constructor(init?: Partial<Patient>) {
    Object.assign(this, init);
    // if coming from JSON where dates are strings:
    if (init?.dateOfBirth) {
      this.dateOfBirth = new Date(init.dateOfBirth);
    }
    if (init?.lastVisitedOn) {
      this.lastVisitedOn = new Date(init.lastVisitedOn);
    }
  }

  /** E.g. "30 - 40" */
  get ageGroup(): string {
    const g = Math.floor(this.age / Patient.AGE_GROUP_PERIOD) * Patient.AGE_GROUP_PERIOD;
    return `${g} - ${g + Patient.AGE_GROUP_PERIOD}`;
  }
}
