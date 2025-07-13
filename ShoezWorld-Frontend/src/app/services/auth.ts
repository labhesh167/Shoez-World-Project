import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) { }

  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  login(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, user);
  }

  // ✅ With token header
  getAllUsers(): Observable<any> {
    const headers = this.createAuthHeaders();
    return this.http.get(`${this.apiUrl}/users`, { headers });
  }

  // ✅ With token header
  deleteUser(username: string): Observable<any> {
    const headers = this.createAuthHeaders();
    return this.http.delete(`${this.apiUrl}/users/${username}`, { headers });
  }

  // Check if user is logged in
  isLoggedIn(): boolean {
    return !!localStorage.getItem('jwtToken');
  }

  // Logout and clear token
  logout() {
    localStorage.removeItem('jwtToken');
  }

  // Decode token and get payload object
  private getTokenPayload(): any {
    const token = localStorage.getItem('jwtToken');
    if (token) {
      const payload = token.split('.')[1];
      return JSON.parse(atob(payload));
    }
    return null;
  }

  // Get logged-in username from token
  getLoggedInUsername(): string | null {
    const payload = this.getTokenPayload();
    return payload ? payload.sub : null;
  }

  // Check if logged-in user is admin
  isAdmin(): boolean {
    const payload = this.getTokenPayload();
    return payload ? payload.role === 'ADMIN' : false;
  }

  // ✅ Helper to attach JWT token in headers
  private createAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('jwtToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
  }
}
