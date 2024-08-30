import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Appointment } from '../models/appointment';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {

  url = "http://localhost:8080/v1/api/appointment"
  
  constructor(private httpClient: HttpClient) { }

  getAppointments(): Observable<Appointment[]> {
    return this.httpClient.get<Appointment[]>(this.url);
  }

  getAppointmentsByPatient(id: string): Observable<Appointment[]> {
    return this.httpClient.get<Appointment[]>(this.url+"/"+id);
  }
}
