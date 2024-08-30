import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Login, Register } from '../../models/auth';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [MatInputModule, FormsModule, MatCardModule, MatButtonModule, MatFormFieldModule, CommonModule],
  templateUrl: './auth.component.html',
  styleUrl: './auth.component.css'
})
export class AuthComponent {
  isLoginMode: boolean = false;

  login: Login = {
    email: "",
    password: ""
  }

  register: Register = {
    name: "",
    email: "",
    password: "",
    age: 0
  }

  confirmPassword: string = '';

  constructor(private authService:AuthService, private router: Router) {}

  ngOnInit(): void {

  }

  onSwitchMode() {
    this.isLoginMode = !this.isLoginMode;
  }

  onSubmit() {
    if (this.isLoginMode) {
      this.authService.login(this.login).subscribe({
        next: (res:any)=> {
            console.log(res)
            localStorage.setItem("token", res.token)
            this.router.navigateByUrl('')  
        },
        error: () => alert("Senha ou usuário inválidos")
      });
    } else {
      this.authService.register(this.register).subscribe({
        next: (res:any) => {
          alert("Successfully registered")
          this.isLoginMode = true;
        }
      })
    }
  }
}
