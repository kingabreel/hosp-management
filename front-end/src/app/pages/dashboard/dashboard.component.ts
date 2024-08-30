import { Component, OnInit } from '@angular/core';
import { AppointmentService } from '../../services/appointment.service';
import { Router } from '@angular/router';
import { Appointment } from '../../models/appointment';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit{
  appointments: Appointment[] = [];

  constructor(private service: AppointmentService, private router: Router, private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    let token;

    if (typeof localStorage !== 'undefined') {
      token = localStorage.getItem("token");
    }
    if(token == null) {
      this.router.navigate(["/auth"]);
    }
    
    this.getAppointments();
  }
  
  getAppointments(): void {
    this.service.getAppointments().subscribe({
      next: (res:any)=> {
        this.appointments = res;
      },
      error: () => {
        this.snackBar.open("System offline", "Close", {
          duration: 5000,
        });
      }
    })
  }
}