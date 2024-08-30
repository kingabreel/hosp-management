import { Component, OnInit } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent implements OnInit{

  constructor(private router: Router) {}
  
  ngOnInit(): void {
    let token;

    if (typeof localStorage !== 'undefined') {
      token = localStorage.getItem("token");
    }
    if(token == null) {
      this.router.navigate(["/auth"]);
    }
  }
}
