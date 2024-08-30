export interface Appointment {
    id: string;
    date: string;
    doctor: Doctor;
    patient: Patient;
}

interface Doctor {
  id: string;
  name: string;
}

interface Patient {
  id: string;
  name: string;
}