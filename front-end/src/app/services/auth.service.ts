import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login, Register } from '../models/auth';
import { User } from '../models/user';
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  url = "http://localhost:8080/v1/api/auth"
  
  constructor(private httpClient: HttpClient) { }

  login(login:Login):Observable<Login> {
    return this.httpClient.post<Login>(this.url+"/login",login)
  }

  register(register:Register): Observable<any> {
    return this.httpClient.post<Register>(this.url+"/register", register);
  }

  getCurrentUser():Observable<User>{
    return this.httpClient.get<User>(this.url+"profile")
  }

}
